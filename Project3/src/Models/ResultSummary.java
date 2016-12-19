package Models;/*import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;*/

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;

import Models.DataPoint;

import java.util.ArrayList;

/**
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

   /* public void saveExcel()
    {
        HSSFWorkbook workbook = new HSSFWorkbook();

        for(int i=0; i< overallResult.size(); i++)
        {
            int rowNum = 0;

            HSSFSheet sheet = workbook.createSheet(String.format("Data Structure %d", i));

            for (Models.TestSummary tS: overallResult.get(i))
            {
                Row row = sheet.createRow(rowNum++);

                row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);

                cell.setCellValue(tS.getName());

                //_______________________________________
                int cellNum = 0;
                row = sheet.createRow(rowNum++);
                cell = row.createCell(cellNum++);

                cell.setCellValue("Number of Elements: ");

                for (Integer j : tS.getIntervals())
                {
                    cell = row.createCell(cellNum++);
                    cell.setCellValue(j);
                }

                cellNum = 0;
                row = sheet.createRow(rowNum++);
                cell = row.createCell(cellNum++);

                cell.setCellValue("CPU Ticks: ");

                for (Long j : tS.getResults())
                {
                    cell = row.createCell(cellNum++);
                    cell.setCellValue(j);
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
    }*/

    public ArrayList<TestSummary> getSummaryFor(int structureNum)
    {
        return overallResult.get(structureNum);
    }

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
