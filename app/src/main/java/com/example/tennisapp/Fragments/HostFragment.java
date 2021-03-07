package com.example.tennisapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tennisapp.R;
import com.example.tennisapp.HelperFragments.ViewPagerFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 *
 * -----------
 * VIEWPAGER
 * -----------
 *
 * newInstance() takes in 3 parameters
 * 1. Title (String)
 * 2. Description (String)
 * 3. Image (Int)
 *
 * Default just returns Title 2 strings and an error image
 */
public class HostFragment extends Fragment {

    ViewPager2 hostViewpager2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HostFragment newInstance(String param1, String param2) {
        HostFragment fragment = new HostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_host, container, false);
        CustomeViewPager2Adapter adapter = new CustomeViewPager2Adapter(getActivity());
        hostViewpager2 = view.findViewById(R.id.hostContent);
        hostViewpager2.setAdapter(adapter);
        hostViewpager2.setPageTransformer(new DepthPageTransformer());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, hostViewpager2, (tab, pos) -> tab.setText("Rule " + (pos + 1))).attach();
    }

    private class CustomeViewPager2Adapter extends FragmentStateAdapter {
        public CustomeViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0: return ViewPagerFragment.newInstance("History","Tennis is a sport that originated in England around the 19th century and is now played in a host of countries around the world. There are four major tournaments known as the ‘majors’ that include Wimbledon, US Open, French Open and Australian tournament.", R.drawable.tennis0);
                case 1: return ViewPagerFragment.newInstance("Object of the Game", "The game of tennis played on a rectangular court with a net running across the centre. The aim is to hit the ball over the net landing the ball within the margins of the court and in a way that results in your opponent being unable to return the ball. You win a point every time your opponent is unable to return the ball within the court.", R.drawable.tennis1);
                case 2: return ViewPagerFragment.newInstance("Players & Equipment", "A tennis match can be played by either one player on each side – a singles match – or two players on each side – a doubles match. The rectangular shaped court has a base line (at the back), service areas (two spaces just over the net in which a successful serve must land in) and two tram lines down either side. A singles match will mean you use the inner side tram line and a doubles match will mean you use the outer tram line.", R.drawable.tennis2);
                case 3: return ViewPagerFragment.newInstance("Scoring", "You need to score four points to win a game of tennis. The points are known as 15 (1 point), 30 (two points), 40 (three points) and the fourth would result in the winning point and the end of that game. If the scores went to 40-40 this would be known as deuce. When a game reaches deuce the player must then win by two clear points.", R.drawable.tennis3);
                case 4: return ViewPagerFragment.newInstance("Winning the Game", "To win the game you must win a certain amount of sets (best of three for women’s matches and best of 5 sets for men’s matches). Winning a set is simply the first player to reach 6 games but have to be clear by at least 2 games. If your opponent wins 5 games you must win the set 7-5. If the set goes to 6-6 then a tie break is played and it’s simply the first player to 7 points.", R.drawable.tennis4);
                default: return ViewPagerFragment.newInstance("error", "401", R.drawable.ic_baseline_error_outline_24);
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    //ZOOM OUT TRANSFORMER
    public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    //DEPTH PAGE TRANSFORMER
    public class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

}