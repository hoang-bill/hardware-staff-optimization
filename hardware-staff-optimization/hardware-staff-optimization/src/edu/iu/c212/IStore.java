package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;

import java.util.List;

public interface IStore {
    List<Item> getItemsFromFile();
    List<Staff> getStaffFromFile();
    void saveItemsToFile(List<Item> items);
    void saveStaffToFile(List<Staff> staff);
    void takeAction();  // To handle all user actions
}
