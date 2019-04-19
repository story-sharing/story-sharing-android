package es.anjon.dyl.storysharing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.anjon.dyl.storysharing.model.Scene;

public class SceneFragment extends Fragment {

    private static final String SCENE_KEY = "SCENE";
    private Scene mScene;

    public static SceneFragment newInstance(Scene scene) {
        SceneFragment fragment = new SceneFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SCENE_KEY, scene);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mScene = (Scene) getArguments().getSerializable(SCENE_KEY);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_scene, container, false);
        rootView.setBackgroundColor(Color.parseColor(mScene.getBackgroundColour()));
        TextView titleView = rootView.findViewById(R.id.title);
        titleView.setText(mScene.getTitle());
        titleView.setTextColor(Color.parseColor(mScene.getTextColour()));

        return rootView;
    }

}
