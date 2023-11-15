package CryptoCharts;
import javafx.scene.*;
import javafx.stage.*;

public class ScenesManager 
{
    public static ScenesManager instance;

    public static ScenesManager getInstance()
    {
        if (instance == null) instance = new ScenesManager();
        return instance;
    }

    public void setMainStage(Stage mainStage) 
    {
        mainStageRef = mainStage;
        //openMainWindow();
        openAuthWindow();
    }

    public void openAuthWindow() 
    { 
        mainStageRef.setScene(authWindow);
    }

    public void openMainWindow()
    {
        mainWindow.constructChart("LINAUSDT", "d");
        mainStageRef.setScene(mainWindow);   
    }


    private ScenesManager()
    {
        authWindow = new AuthWindow();
        mainWindow = new MainWindow();
    }

    private AuthWindow authWindow;
    private MainWindow mainWindow;
    private Stage mainStageRef;
    
}
