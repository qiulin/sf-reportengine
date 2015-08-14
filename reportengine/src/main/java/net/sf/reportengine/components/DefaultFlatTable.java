/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.components;

import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.OpenLoopCloseInputAlgo;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndTableExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.FlatTableTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartTableInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.in.MultipleExternalSortedFilesTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.ReportUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default implementation for FlatTable
 * 
 * @author dragos balan
 *
 */
final class DefaultFlatTable extends AbstractColumnBasedTable {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(DefaultFlatTable.class);

    /**
     * the container for two potential algorithms : 1. the sorting algorithm 2.
     * the reporting algorithmn
     */
    private AlgorithmContainer reportAlgoContainer = new AlgorithmContainer();

    /**
     * the one and only constructor based on a builder
     * 
     * @param builder
     *            the helper builder for this class
     */
    DefaultFlatTable(TableInput input,
                     List<DataColumn> dataColumns,
                     List<GroupColumn> groupColumns,
                     boolean showTotals,
                     boolean showGrandTotal,
                     boolean showDataRows,
                     boolean valuesSorted) {
        super(input, dataColumns, groupColumns, showTotals, showGrandTotal, showDataRows, valuesSorted);
    }

    /**
     * reportAlgo configuration
     */
    @Override
    protected void config() {
        LOGGER.trace("configuring flat report");

        boolean needsProgramaticSorting =
            !hasValuesSorted() || ReportUtils.isSortingInColumns(getGroupColumns(),
                                                                 getDataColumns());
        LOGGER.info("programatic sorting needed {} ", needsProgramaticSorting);

        if (needsProgramaticSorting) {
            reportAlgoContainer.addAlgo(configSortingAlgo());
        }
        reportAlgoContainer.addAlgo(configReportAlgo(needsProgramaticSorting));
    }

    /**
     * configuration of the sorting algorithm
     * 
     * @return
     */
    private Algorithm configSortingAlgo() {
        MultiStepAlgo sortingAlgo = new OpenLoopCloseInputAlgo() {
            @Override
            protected TableInput
                    buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
                return (TableInput) inputParams.get(AlgoIOKeys.TABLE_INPUT);
            }
        };

        // main steps
        sortingAlgo.addMainStep(new ExternalSortPreparationStep());

        // exit steps
        return sortingAlgo;
    }

    /**
     * configures the report algorithm
     * 
     * @return
     */
    private Algorithm configReportAlgo(final boolean hasBeenPreviouslySorted) {
        MultiStepAlgo reportAlgo = new OpenLoopCloseInputAlgo() {
            @Override
            protected TableInput
                    buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
                if (hasBeenPreviouslySorted) {
                    // if the input has been previously sorted
                    // then the sorting algorithm ( the previous) has created
                    // external sorted files
                    // which will serve as input from this point on
                    return new MultipleExternalSortedFilesTableInput((List<File>) inputParams.get(AlgoIOKeys.SORTED_FILES), new NewRowComparator((List<GroupColumn>) inputParams.get(AlgoIOKeys.GROUP_COLS), (List<DataColumn>) inputParams.get(AlgoIOKeys.DATA_COLS)));
                } else {
                    return (TableInput) inputParams.get(AlgoIOKeys.TABLE_INPUT);
                }
            }
        };

        reportAlgo.addInitStep(new InitReportDataInitStep());
        reportAlgo.addInitStep(new FlatReportExtractTotalsDataInitStep());// TODO:
                                                                          // only
                                                                          // when
                                                                          // report
                                                                          // has
                                                                          // totals
        reportAlgo.addInitStep(new StartTableInitStep());
        reportAlgo.addInitStep(new ColumnHeaderOutputInitStep());

        // then we add the main steps
        reportAlgo.addMainStep(new GroupLevelDetectorStep());

        if (getShowTotals() || getShowGrandTotal()) {
            reportAlgo.addMainStep(new FlatTableTotalsOutputStep());
            reportAlgo.addMainStep(new TotalsCalculatorStep());
        }

        if (getShowDataRows()) {
            reportAlgo.addMainStep(new DataRowsOutputStep());
        }

        if (getGroupColumns() != null && getGroupColumns().size() > 0) {
            reportAlgo.addMainStep(new PreviousRowManagerStep());
        }

        reportAlgo.addExitStep(new EndTableExitStep());

        return reportAlgo;
    }

    /**
     * validation of configuration
     */
    @Override
    protected void validate() {
        LOGGER.trace("validating flat report");
        // validate non null input and output
        super.validate();

        List<DataColumn> dataColumns = getDataColumns();
        if (dataColumns == null || dataColumns.size() == 0) {
            throw new ConfigValidationException("The report needs at least one data column to work properly");
        }

        // if totals are needed then check if any GroupCalculators have been
        // added to ALL DataColumns
        if ((getShowTotals() || getShowGrandTotal()) && !ReportUtils.atLeastOneDataColumHasCalculators(dataColumns)) {
            throw new ConfigValidationException("Please configure a GroupCalculator to at least one DataColumn in order to display totals");
        }
    }

    /**
     * Call this method for execution of the report<br>
     * What this method does: <br>
     * <ul>
     * <li>validates the configuration - validateConfig() method call</li>
     * <li>opens the output - output.open()</li>
     * <li>runs each reportAlgo execute() method</li>
     * <li>closes the output - output.close()</li>
     * </ul>
     */
    public void output(AbstractReportOutput reportOutput) {
        validate();
        config();

        // preparing the context of the report reportAlgo
        Map<AlgoIOKeys, Object> inputParams =
            new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
        inputParams.put(AlgoIOKeys.TABLE_INPUT, getInput());
        inputParams.put(AlgoIOKeys.NEW_REPORT_OUTPUT, reportOutput);
        inputParams.put(AlgoIOKeys.DATA_COLS, getDataColumns());
        inputParams.put(AlgoIOKeys.GROUP_COLS, getGroupColumns());
        inputParams.put(AlgoIOKeys.SHOW_TOTALS, getShowTotals());
        inputParams.put(AlgoIOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal());

        Map<AlgoIOKeys, Object> result =
            reportAlgoContainer.execute(inputParams);
        LOGGER.info("flat table output ended with result {}", result);
    }
}
