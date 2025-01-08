package com.travel.dao;

import com.travel.model.TravelPackage;
import java.util.List;

public interface TravelPackageDAO {
    void addPackage(TravelPackage package);
    TravelPackage getPackageById(int id);
    List<TravelPackage> getAllPackages();
    void updatePackage(TravelPackage package);
    void deletePackage(int id);
}
