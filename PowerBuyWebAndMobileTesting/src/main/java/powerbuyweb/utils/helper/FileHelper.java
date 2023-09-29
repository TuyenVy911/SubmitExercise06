package powerbuyweb.utils.helper;

import java.io.File;
import org.slf4j.Logger;

public class FileHelper {
  private static String TEST_DATA_FOLDER = "testdata";
  private static String RESOURCES_FOLDER = "resources";

  private static String JSON_EXTENSION = ".json";
  private static String EXCEL_EXTENSION = ".xlsx";

  private static String SOURCE_FOLDER = "src";

  private static String MAIN_FOLDER = "main";

  private static String USER_DIR = "user.dir";

  private static String SOURCE_TARGET_FOLDER = "target";
  private static String SOURCE_ALLURE_RESULTS_FOLDER = "allure-results";

  private static String SOURCE_FILES_FOLDER = "files";
  private static String SOURCE_AUTOIT_FOLDER = "autoIt";

  private static Logger logger = LogHelper.getLogger();
  public static String getDataJSONFilePath(String dataJsonName){
    logger.info(System.getProperty(USER_DIR) + File.separator + SOURCE_FOLDER + File.separator
        + MAIN_FOLDER + File.separator + RESOURCES_FOLDER + File.separator + TEST_DATA_FOLDER + File.separator
        + dataJsonName + JSON_EXTENSION);
    String correctFilePath = System.getProperty(USER_DIR) + File.separator + SOURCE_FOLDER + File.separator
        + MAIN_FOLDER + File.separator + RESOURCES_FOLDER + File.separator + TEST_DATA_FOLDER + File.separator
        + dataJsonName + JSON_EXTENSION;

    //System.getProperty("user.dir") = C:\Users\dovan\OneDrive - ptit.edu.vn\Desktop\SeleniumPractice\SeleniumPractice
    return correctFilePath;
  }

  public static String getTargetAllureResultsFilePath(){
    //C:\Users\dovan\OneDrive - ptit.edu.vn\Desktop\SeleniumPractice\FirstAutoTestProject\target
    logger.info(System.getProperty(USER_DIR) + File.separator + SOURCE_TARGET_FOLDER + File.separator + SOURCE_ALLURE_RESULTS_FOLDER);
    String allureResultsFilePath = System.getProperty(USER_DIR) + File.separator + SOURCE_TARGET_FOLDER + File.separator + SOURCE_ALLURE_RESULTS_FOLDER;

    return allureResultsFilePath;
  }

  public static String getFilePathOneSeparator(String fileName){
    logger.info(System.getProperty(USER_DIR) + File.separator + SOURCE_FILES_FOLDER + File.separator + fileName);
    String uploadFilePath = System.getProperty(USER_DIR) + File.separator + SOURCE_FILES_FOLDER + File.separator + fileName;

    return uploadFilePath;
  }

  public static String getAutoItFilePath(String fileName){
    logger.info(System.getProperty(USER_DIR) + File.separator + SOURCE_AUTOIT_FOLDER + File.separator + fileName);
    String uploadFilePath = System.getProperty(USER_DIR) + File.separator + SOURCE_AUTOIT_FOLDER + File.separator + fileName;

    return uploadFilePath;
  }

  public static String getFilePathTwoSeparator(String fileName){
    logger.info(System.getProperty(USER_DIR) + File.separator + SOURCE_FILES_FOLDER + File.separator + fileName);
    String uploadFilePath = System.getProperty(USER_DIR) + File.separator + SOURCE_FILES_FOLDER + File.separator + fileName;

    return uploadFilePath;
  }

  public static String getExcelDataFilePath(String excelFileName){
    logger.info(System.getProperty(USER_DIR) + File.separator + SOURCE_FOLDER + File.separator
        + MAIN_FOLDER + File.separator + RESOURCES_FOLDER + File.separator + TEST_DATA_FOLDER + File.separator
        + excelFileName + EXCEL_EXTENSION);
    String correctFilePath = System.getProperty(USER_DIR) + File.separator + SOURCE_FOLDER + File.separator
        + MAIN_FOLDER + File.separator + RESOURCES_FOLDER + File.separator + TEST_DATA_FOLDER + File.separator
        + excelFileName + EXCEL_EXTENSION;

    return correctFilePath;
  }
}
