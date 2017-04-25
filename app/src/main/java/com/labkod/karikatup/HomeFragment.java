package com.labkod.karikatup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ListView listComics;

    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_tab, container, false);

        listComics = (ListView) view.findViewById(R.id.comicList);
        // Construct the data source
        ArrayList<Comic> arrayOfUsers = new ArrayList<Comic>();
        ComicAdapter adapter = new ComicAdapter(view.getContext(), arrayOfUsers);
        // Attach the adapter to a ListView
        listComics.setAdapter(adapter);

        // Add item to adapter
        Comic newComic = new Comic("ALiFii", "http://3.bp.blogspot.com/-SbGTC4Ai97E/UjnWxQaZZsI/AAAAAAAAAS0/JydvTsm9VS0/s1600/bisiy-yaparim-ben-bunla-ki.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://3.bp.blogspot.com/-SbGTC4Ai97E/UjnWxQaZZsI/AAAAAAAAAS0/JydvTsm9VS0/s1600/bisiy-yaparim-ben-bunla-ki.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://galeri2.uludagsozluk.com/339/git-la-bu-mahalleden_394812.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://3.bp.blogspot.com/-SbGTC4Ai97E/UjnWxQaZZsI/AAAAAAAAAS0/JydvTsm9VS0/s1600/bisiy-yaparim-ben-bunla-ki.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://3.bp.blogspot.com/-SbGTC4Ai97E/UjnWxQaZZsI/AAAAAAAAAS0/JydvTsm9VS0/s1600/bisiy-yaparim-ben-bunla-ki.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://3.bp.blogspot.com/-SbGTC4Ai97E/UjnWxQaZZsI/AAAAAAAAAS0/JydvTsm9VS0/s1600/bisiy-yaparim-ben-bunla-ki.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://galeri2.uludagsozluk.com/339/git-la-bu-mahalleden_394812.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);
        newComic = new Comic("ALiFii", "http://3.bp.blogspot.com/-SbGTC4Ai97E/UjnWxQaZZsI/AAAAAAAAAS0/JydvTsm9VS0/s1600/bisiy-yaparim-ben-bunla-ki.jpg", "24 May, 20:40", "Yiğit Özgür", 2400, 100);
        adapter.add(newComic);

        return view;
    }
}

