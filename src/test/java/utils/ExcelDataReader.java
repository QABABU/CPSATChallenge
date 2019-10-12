package utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

public class ExcelDataReader {

    private static XSSFSheet ExcelWSheet;

    private static XSSFRow Row;


    @DataProvider(name = "get-data")
    public static Object[][] getData(Method method) throws Exception {

        String[][] tabArray = new String[1][1];

        String testName = method.getName();

        String file_path = System.getProperty("user.dir")+"\\src\\main\\resources\\TestData.xlsx";

        try {

            FileInputStream ExcelFile = new FileInputStream(file_path);

            XSSFWorkbook excelWBook = new XSSFWorkbook(ExcelFile);

            ExcelWSheet = excelWBook.getSheet("data");

            int totalRows = ExcelWSheet.getLastRowNum();

            System.out.println("Total rows: "+totalRows);

            int currentRow = -1;

            for(int i=0; i <= totalRows; i++){

                XSSFRow row = ExcelWSheet.getRow(i);

                if(row != null){

                    String value = row.getCell(0).getStringCellValue();

                    if(value.equalsIgnoreCase(testName)){

                        currentRow = i;
                        break;
                    }
                }
            }

            System.out.println(ExcelWSheet.getRow(currentRow).getCell(1).getStringCellValue());

            tabArray[0][0] = ExcelWSheet.getRow(currentRow).getCell(1).getStringCellValue();


        }catch (FileNotFoundException e){

            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();

        }catch (IOException e){

            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

        return(tabArray);
    }

    private static String getCellData(int RowNum, int ColNum) throws Exception {

        try{
            XSSFCell cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

            return cell.getStringCellValue();

            }catch (Exception e){

                System.out.println(e.getMessage());

                throw (e);

            }

        }
}
