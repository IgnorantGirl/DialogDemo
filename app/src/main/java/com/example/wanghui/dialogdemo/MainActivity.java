package com.example.wanghui.dialogdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePicker datePicker;
    private String[] weeks = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    private String date;
    private TextView edit_date;
    private TextView check_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button  date_btn = findViewById(R.id.date_dialog_btn);
        date_btn.setOnClickListener(this);
        Button radio_btn = findViewById(R.id.radio_dialog_btn);
        radio_btn.setOnClickListener(this);
        Button check_btn = findViewById(R.id.checkbox_dialog_btn);
        check_btn.setOnClickListener(this);
        check_context = findViewById(R.id.checkbox_main_txt);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_dialog_btn:
                dateDialogShow();
                break;
            case R.id.radio_dialog_btn:
                radioDialogShow();
                break;
            case R.id.checkbox_dialog_btn:
                checkboxDialogShow();
                break;
        }
    }

    private void checkboxDialogShow() {
        final String[] items = {"编程","旅游","健身","追剧"};
        View v = LayoutInflater.from(this).inflate(R.layout.title_layout,null);
        boolean[] isChecks = {false,false,false,false};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(R.drawable.xingxing)
                .setTitle("个人喜好")
               // .setCustomTitle(v)
               .setMultiChoiceItems(items,isChecks , new DialogInterface.OnMultiChoiceClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                       check_context.append(items[which].toString()+" ");
                       Toast.makeText(MainActivity.this,"你选择了："+items[which].toString(),Toast.LENGTH_SHORT).show();
                   }
               });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this,"你选择了："+check_context.getText(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
       builder.show();
        check_context.setText("个人喜好");
        check_context.append("\n");
    }

    private void radioDialogShow() {
        final String[] itemsRadio = {"男","女","你猜","未知"};
        View v = LayoutInflater.from(this).inflate(R.layout.radio_layout,null);
      //  ArrayAdapter adapter = new ArrayAdapter(this,R.layout.radio_layout,R.id,items);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(v);
//                 .setSingleChoiceItems(itemsRadio,0 , new DialogInterface.OnClickListener() {
//                     @Override
//                     public void onClick(DialogInterface dialog, int which) {
//                         Toast.makeText(MainActivity.this,"你选择了："+itemsRadio[which],Toast.LENGTH_SHORT).show();
//                         dialog.dismiss();
//                     }
//                 });


        // builder.show();
        //由于AlertDialog.Builder没有dismiss()方法，所以采用得第二种方法 AlertDialog种有dismiss()方法
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

       RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        final RadioButton male = v.findViewById(R.id.male);
        final RadioButton female = v.findViewById(R.id.female);
        final RadioButton noidea = v.findViewById(R.id.noidea);
        final RadioButton guess = v.findViewById(R.id.guess);
       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.male:
                       Toast.makeText(MainActivity.this,"你选择了："+male.getText(),Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                       break;
                   case R.id.female:
                       Toast.makeText(MainActivity.this,"你选择了："+female.getText(),Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                       break;
                   case R.id.noidea:
                       Toast.makeText(MainActivity.this,"你选择了："+noidea.getText(),Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                       break;
                   case R.id.guess:
                       Toast.makeText(MainActivity.this,"你选择了："+guess.getText(),Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                       break;
               }
           }
       });

    }

    private void dateDialogShow() {
         check_context.clearComposingText();
        View v = LayoutInflater.from(this).inflate(R.layout.date_layout,null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                             .setView(v);

        // builder.show();
        //由于AlertDialog.Builder没有dismiss()方法，所以采用得第二种方法 AlertDialog种有dismiss()方法
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
         datePicker =  v.findViewById(R.id.date_picker);
         edit_date = v.findViewById(R.id.edit_date);
        Button done = v.findViewById(R.id.done_btn);
        date = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() + "  ";
        dateShow(datePicker.getFirstDayOfWeek(),date);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
              @Override
              public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                  Log.d("Tag",view.getFirstDayOfWeek()+" ");
                   date = year + "-" +(monthOfYear+1) + "-" + dayOfMonth+"  ";
                  //解决week的问题
                  Calendar calendar = Calendar.getInstance();
                  calendar.set(year,monthOfYear,dayOfMonth);
                  dateShow(calendar.get(Calendar.DAY_OF_WEEK),date);

              }
          });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你选择了："+datePicker.getYear() + "-" +(datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth(),Toast.LENGTH_SHORT).show();
                 alertDialog.dismiss();
            }
        });
    }

    private void dateShow(int weekIndex,String  mdate) {
        switch (weekIndex){
            case 1:
                edit_date.setText(mdate+ weeks[0]);
                break;
            case 2:
                edit_date.setText(mdate + weeks[1]);
                break;
            case 3:
                edit_date.setText(mdate + weeks[2]);
                break;
            case 4:
                edit_date.setText(mdate + weeks[3]);
                break;
            case 5:
                edit_date.setText(mdate + weeks[4]);
                break;
            case 6 :
                edit_date.setText(mdate + weeks[5]);
                break;
            case 7:
                edit_date.setText(mdate+ weeks[6]);
                break;
        }

    }
}
