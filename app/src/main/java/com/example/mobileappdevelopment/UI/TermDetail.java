package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Courses;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetail extends AppCompatActivity {
    String title;
    double price;
    int termId;
    EditText editName;
    EditText editPrice;
    Repository repository;
    int numParts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editName=findViewById(R.id.titletext);
        editPrice=findViewById(R.id.pricetext);
        termId = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        editName.setText(title);
        editPrice.setText(Double.toString(price));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermDetail.this, CourseDetail.class);
                intent.putExtra("prodID", termId);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Courses> filteredParts = new ArrayList<>();
//        for (Courses c : repository.getAllCourses()) {
//            if (c.getTermId() == termId) filteredParts.add(p);
//        }
//        partAdapter.setParts(filteredParts);

    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_productdetails, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()== R.id.productsave){
//            Product product;
//            if (productID==-1){
//                if (repository.getmAllProducts().size() == 0) productID = 1;
//                else productID = repository.getmAllProducts().get(repository.getmAllProducts().size() - 1).getProductID() + 1;
//                product = new Product(productID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
//                repository.insert(product);
//                this.finish();
//            }
//            else{
//                product = new Product(productID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
//                repository.update(product);
//                this.finish();
//            }
//        }
//        if(item.getItemId()== R.id.productdelete){
//            for(Product prod:repository.getmAllProducts()){
//                if(prod.getProductID()==productID)currentProduct=prod;
//            }
//            numParts=0;
//            for(Part part: repository.getAllParts()){
//                if(part.getProductID()==productID)++numParts;
//            }
//            if(numParts==0){
//                repository.delete(currentProduct);
//                Toast.makeText(ProductDetails.this, currentProduct.getProductName() + " was deleted",Toast.LENGTH_LONG).show();
//                ProductDetails.this.finish();
//            }
//            else{
//                Toast.makeText(ProductDetails.this, "Can't delete a product with parts",Toast.LENGTH_LONG).show();
//            }
//        }
//        return true;
//    }
    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView=findViewById(R.id.partrecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourse = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermId() == termId) filteredCourse.add(c);
        }
        courseAdapter.setmCourse(filteredCourse);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}