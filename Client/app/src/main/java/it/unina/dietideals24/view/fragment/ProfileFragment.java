package it.unina.dietideals24.view.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import it.unina.dietideals24.R;

public class ProfileFragment extends Fragment {

    ImageView imageProfile;
    ActivityResultLauncher<PickVisualMediaRequest> singlePhotoPickerLauncher;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        Button editProfileBtn = view.findViewById(R.id.editProfileBtn);

        initializeSinglePhotoPickerLauncher();

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog(view);
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog(view);
            }
        });

        return view;
    }

    private void initializeSinglePhotoPickerLauncher() {
        singlePhotoPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri == null) {
                    Toast.makeText(getContext(), "Seleziona immagine!", Toast.LENGTH_SHORT).show();
                } else {
                    Glide.with(getActivity()).load(uri).into(imageProfile);
                }
            }
        });
    }

    private void showChangePasswordDialog(View view) {
        ConstraintLayout changePasswordConstraintLayout = view.findViewById(R.id.changePasswordConstraintLayout);
        View viewChangePasswordDialog = LayoutInflater.from(getContext()).inflate(R.layout.change_password_dialog, changePasswordConstraintLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewChangePasswordDialog);
        final AlertDialog alertDialog = builder.create();

        Button changePasswordBtn = viewChangePasswordDialog.findViewById(R.id.changePasswordBtn);
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void showEditProfileDialog(View view) {
        ConstraintLayout editProfileConstraintLayout = view.findViewById(R.id.editProfileConstraintLayout);
        View viewEditProfileDialog = LayoutInflater.from(getContext()).inflate(R.layout.edit_profile_dialog, editProfileConstraintLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewEditProfileDialog);
        final AlertDialog alertDialog = builder.create();

        Button changeImgBtn = viewEditProfileDialog.findViewById(R.id.changeImgBtn);
        imageProfile = viewEditProfileDialog.findViewById(R.id.imageProfile);

        changeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the photo picker and let the user choose only images
                singlePhotoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }
}