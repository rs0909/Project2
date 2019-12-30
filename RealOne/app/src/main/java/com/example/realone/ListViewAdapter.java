package com.example.realone;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Contact> contactModelArrayList;

    public ListViewAdapter(Context context, ArrayList<Contact> contactModelArrayList){
        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }

//    @Override
//    public int getViewTypeCount() {
//        return getCount();
//    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return contactModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactItemView view = new ContactItemView(context);
//        ContactList contactList = new ContactList(context);
//        ArrayList<Contact> arrayList = contactList.getContactList();
        //어떤 뷰든 안드로이드에서는 Context객체를 받게 되어있으므로 getApplicationCotext로 넣어줍니다.

        //이제 이 뷰를 반환해주면 되는데 이 뷰가 몇 번째 뷰를 달라는 것인지 position값이 넘어오므로
        Contact item  = contactModelArrayList.get(position); //SigerItem은 참고로 Dataset임. 따로 기본적인것만 구현해놓음
        //이 position값을 갖는 아이템의 SigerItem객체를 새로 만들어준 뒤
        view.setName(item.getName());
        view.setImage(item.getPhoto());
        //이렇게 해당 position에 맞는 값으로 설정해줍니다.

        //그렇게 설정을 잘 해놓은 다음에 view를 반환해야 데이터값이 들어간 레이아웃이 반환될거에요~
        return view;
    }
}
