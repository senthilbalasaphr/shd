import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataServiceLogic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readFileData();

	}

	public static void readFileData() {
		List<List<MetaDataObj1>> rowList = new ArrayList<List<MetaDataObj1>>();

		List<MetaDataObj1> columnList = null;
		List<MetaDataObj1> headerList = null;

		MetaDataObj1 metaDataObj = null;
		try {

			// TODO Auto-generated method stub
			FileInputStream file = new FileInputStream(new File("C:\\Users\\Dhinesh\\Downloads\\Department.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
//			Iterator<Row> rowIterator = sheet.iterator();
			Iterator<Row> rowIterator = sheet.rowIterator();
			int rowCount = 1;
			int totalColumnSize = getColumnSize();
//			int columnCount;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
//				Iterator<Cell> cellIterator = row.cellIterator();
				Iterator<Cell> cellIterator = row.iterator();

				if (rowCount > 6) {
					columnList = new ArrayList<MetaDataObj1>();
				} else {
					headerList = new ArrayList<MetaDataObj1>();
				}

				// ======================
//				columnCount = 1;
				for (int cn = 0; cn < row.getLastCellNum(); cn++) {
					if (cn >= totalColumnSize) {
						break;
					}
					metaDataObj = new MetaDataObj1();
					Cell cell = row.getCell(cn);

					if (cell == null) {
						// This cell is empty/blank/un-used, handle as needed
						System.out.println("cellStr null : ");
					} else {
						String cellStr = "";
						switch (cell.getCellType()) {
						case STRING:
							cellStr = cell.getStringCellValue();
							if (rowCount > 6) {
								metaDataObj.setFieldValue(getMappingValue(cellStr));
							} else {
								metaDataObj.setFieldValue(cellStr);
							}
							metaDataObj.setFieldType("STRING");
							metaDataObj.setFieldName("");

							break;
						case NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(row.getCell(cn))) {
								System.out.println(
										"Row No.: " + row.getRowNum() + " " + row.getCell(cn).getDateCellValue());
							} else {
								cellStr = String.valueOf(cell.getNumericCellValue());
								if (rowCount > 6) {
									metaDataObj.setFieldValue(getMappingValue(cellStr));
								} else {
									metaDataObj.setFieldValue(cellStr);
								}
							}
							metaDataObj.setFieldType("NUMERIC");
							metaDataObj.setFieldName("");
							break;
						case BLANK:
							cellStr = String.valueOf(cell.getStringCellValue());
							if (rowCount > 6) {
								metaDataObj.setFieldValue(getMappingValue(cellStr));
							} else {
								metaDataObj.setFieldValue(cellStr);
							}
							metaDataObj.setFieldType("STRING");
							metaDataObj.setFieldName("");
							break;
						}

						// Do something with the value
					}

					if (rowCount > 6) {
						columnList.add(metaDataObj);
					} else {
						headerList.add(metaDataObj);
					}
//					columnCount++;
				}
				// =======================
				if (rowCount > 6) {
					rowList.add(columnList);
				} else {
					rowList.add(headerList);
				}
				rowCount++;
//				System.out.println("");
			}
			file.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		System.out.println("Finaly obj result :" + rowList.toString());
		writeExcel(rowList);

	}

	// fetch the value from database odata
	public static String getMappingValue(String legacyValue) {
		return legacyValue;
	}

	// fetch the value from database odata
	public static int getColumnSize() {
		return 24;
	}

	public static void writeExcel(List<List<MetaDataObj1>> excelData) {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Department_Odata");

		String fieldValue = "";
		String fieldType = "";

		int rowNum = 0;
		System.out.println("Creating excel");

		for (List<MetaDataObj1> rowData : excelData) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (MetaDataObj1 columnData : rowData) {
				Cell cell = row.createCell(colNum++);
				fieldValue = columnData.getFieldValue();
				fieldType = columnData.getFieldType();
//				if (fieldType.equals("STRING")) {
				cell.setCellValue(fieldValue);
//				cell.setCellFormula(fieldType);
//				} else if (fieldType.equals("NUMERIC")) {
//					cell.setCellValue(fieldValue);
//				}

			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Dhinesh\\Downloads\\Department_Odata.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

}
