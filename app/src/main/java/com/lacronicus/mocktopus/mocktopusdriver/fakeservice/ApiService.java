package com.lacronicus.mocktopus.mocktopusdriver.fakeservice;

import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyCollectionContainingModel;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.model.MyModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;

/**
 * Created by fdoyle on 7/10/14.
 */
public interface ApiService {
    @GET("/foo")
    public MyModel returnMyModel();
    public MyCollectionContainingModel returnMyCollectionContainingModel();
    //public MyCollectionExtendingModel returnMyCollectionExtendingModel(); //todo fix this one
    public List<MyModel> returnMyModelList();
    public ArrayList<MyModel> returnMyModelArrayList();
}
