package start;
import service.*;
import model.*;
import importexport.*;
import view.frame.MainFrame;

import javax.swing.*;
import java.util.List;

//extends PublisherImplementation
public class AppCore  {

    IImportExport importExport;
    AbstractStorage storage;
    IObjectConverter objectConverter;
    OrderProvider orderProvider;
    Crawler crawler;

    public AppCore() {
         importExport = core.RepositoryManager.getImportExport();
		 storage = core.RepositoryManager.getStorage();
		 objectConverter = core.RepositoryManager.getObjectConverter();
		 orderProvider = new OrderProvider();
		 crawler = new Crawler();

    }

    public OrderProvider getOrderProvider() {
        return orderProvider;
    }

    public Crawler getCrawler() {
        return crawler;
    }

    public IImportExport getImportExport() {
        return importExport;
    }

    public AbstractStorage getStorage() {
        return storage;
    }

    public IObjectConverter getObjectConverter() {
        return objectConverter;
    }

    public JTable loadTable(List<Entity> entities){
          String[] columnNames = {"ID",
                "Type",
                "Attributes",
                "Nested Entity"};
        Object[][] data = new Object[3000][4];
        int i = 0;
        for(Entity e : entities){
            data[i][0] = e.getId();
            data[i][1] = e.getType();
            data[i][2] = e.getAttributes().toString();
            data[i][3] = e.getNestedEntities().toString();
            i++;
        }
        return  new JTable(data,columnNames);
    }

}

