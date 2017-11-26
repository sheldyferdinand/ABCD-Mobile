package proyekpakdani.abcd.questionsUtils.fragment;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import proyekpakdani.abcd.R;
import proyekpakdani.abcd.questionsUtils.SurveyActivity;
import proyekpakdani.abcd.questionsUtils.models.Question;

/**
 * Created by SLD on 26-Nov-17.
 */

public class FragmentImage extends Fragment{

    private FragmentActivity mContext;
    private Button button_continue;
    private Button button_take;
    private TextView textview_q_title;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_image, container, false);

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        imageView = (ImageView) rootView.findViewById(R.id.image_photo);

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ((SurveyActivity) mContext).go_to_next();
            }
        });

        button_take.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        return rootView;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        Question q_data = (Question) getArguments().getSerializable("data");

        textview_q_title.setText(q_data != null ? q_data.getQuestionTitle() : "");

        if (q_data.getRequired()) {
            button_continue.setVisibility(View.GONE);
        }

        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            button_continue.setVisibility(View.VISIBLE);
        }
    }
}

