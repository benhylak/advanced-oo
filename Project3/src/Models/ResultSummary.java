package Models;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Class for handling the summary of all results from all tests ran on a range of data structures. Creates excel
 * documents, can print to console...
 *
 * Created by benhylak on 12/5/16.
 */
public class ResultSummary
{
    ArrayList<ArrayList<TestSummary>> overallResult;

    public ResultSummary()
    {
        overallResult = new ArrayList<ArrayList<TestSummary>>();
    }

    public void addStructureResult(ArrayList<TestSummary> indivResult)
    {
        overallResult.add(indivResult);
    }

    /**
     * Saves data to excel. Each structure is in it's own sheet of the workbook
     */
    public void saveExcel()
    {
        HSSFWorkbook workbook = new HSSFWorkbook();

        for(int i=0; i< overallResult.size(); i++)
        {
            int rowNum = 0;

            HSSFSheet sheet = workbook.createSheet(String.format("Data Structure %d", i));

            for (Models.TestSummary tS: overallResult.get(i))
            {
                Row row = sheet.createRow(rowNum++); //spacer row

                row = sheet.createRow(rowNum++); //new row for name of test
                Cell cell = row.createCell(0);

                cell.setCellValue(tS.getName());

                //_______________________________________
                int nCellNum = 0;
                int valCellNum = 0;

                Row nRow = sheet.createRow(rowNum++); //row for intervals
                Row valRow = sheet.createRow(rowNum++); //row for values

                cell = nRow.createCell(nCellNum++);
                cell.setCellValue("Number of Elements: ");

                cell = valRow.createCell(valCellNum++);
                cell.setCellValue("CPU Ticks: ");

                for (DataPoint<Integer, Long> p : tS)
                {
                    Cell nCell = nRow.createCell(nCellNum++);
                    nCell.setCellValue(p.getN());

                    Cell valCell = valRow.createCell(valCellNum++);
                    valCell.setCellValue(p.getVal());
                }
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("/Volumes/Macintosh HD/Users/benhylak/results.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<TestSummary> getSummaryFor(int structureNum)
    {
        return overallResult.get(structureNum);
    }

    /**
     * Print data to console
     */
    public void printData()
    {
        for(int i=0; i < overallResult.size(); i++)
        {
            System.out.println(String.format("Data Structure %d", i+1));

            for(TestSummary ts : overallResult.get(i))
            {
                System.out.println("\t" + ts.getName());
                StringBuilder sb = new StringBuilder();

                for (DataPoint<Integer, Long> p : ts)
                {
                    sb.append(String.format(" | O(%d): %d ", p.getN(), p.getVal()));
                }

                System.out.println("\t\t" + sb.toString());
            }
        }
    }

    public int numOfStructures()
    {
        return overallResult.size();
    }
}
