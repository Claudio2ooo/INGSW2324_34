package it.unina.dietideals24.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
import it.unina.dietideals24.utils.localstorage.TokenManagement;
import it.unina.dietideals24.view.activity.LoginActivity;

public class ProfileFragment extends Fragment {

    private TextView userFullNameText;
    private TextView userEmailText;
    private TextView geographicalAreaText;
    private TextView biographyText;
    private TextView linksText;
    private TextView titleSectionBiography;
    private TextView titleSectionLinks;
    private TextView messageCompleteYourProfileText;
    private EditText inputNameEditText;
    private EditText inputGeographicalAreaEditText;
    private EditText inputSurnameEditText;
    private EditText inputBiographyEditText;
    private EditText inputLinksEditText;
    private ImageView imageProfile;
    private ActivityResultLauncher<PickVisualMediaRequest> singlePhotoPickerLauncher;
    private Button changePasswordBtn;
    private Button editProfileBtn;
    private Button logOutBtn;
    private DietiUser localDietiUser;

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

        localDietiUser = LocalDietiUser.getLocalDietiUser(getActivity());

        initializeViews(view);
        setTextViewWithLocalDietiUserData();
        initializeSinglePhotoPickerLauncher();

        messageCompleteYourProfileText.setOnClickListener(v -> showEditProfileDialog(view));
        editProfileBtn.setOnClickListener(v -> showEditProfileDialog(view));
        changePasswordBtn.setOnClickListener(v -> showChangePasswordDialog(view));

        logOutBtn.setOnClickListener(v -> {
            TokenManagement.deleteTokenData();
            LocalDietiUser.deleteLocalDietiUser(getActivity());

            Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
            startActivity(loginActivity);
        });

        return view;
    }

    private void initializeViews(View view) {
        userFullNameText = view.findViewById(R.id.userFullNameText);
        userEmailText = view.findViewById(R.id.userEmailText);
        geographicalAreaText = view.findViewById(R.id.geographicalAreaText);
        biographyText = view.findViewById(R.id.biographyText);
        linksText = view.findViewById(R.id.linksText);

        titleSectionBiography = view.findViewById(R.id.titleSectionBiography);
        titleSectionLinks = view.findViewById(R.id.titleSectionLinks);
        messageCompleteYourProfileText = view.findViewById(R.id.messageCompleteYourProfileText);
        messageCompleteYourProfileText.setVisibility(View.GONE);

        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        editProfileBtn = view.findViewById(R.id.editProfileBtn);
        logOutBtn = view.findViewById(R.id.logOutBtn);
    }

    private void initializeSinglePhotoPickerLauncher() {
        singlePhotoPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri == null) {
                Toast.makeText(getContext(), "Seleziona immagine!", Toast.LENGTH_SHORT).show();
            } else {
                Glide.with(getActivity()).load(uri).into(imageProfile);
            }
        });
    }

    private void showChangePasswordDialog(View view) {
        ConstraintLayout changePasswordConstraintLayout = view.findViewById(R.id.changePasswordConstraintLayout);
        View viewChangePasswordDialog = LayoutInflater.from(getContext()).inflate(R.layout.change_password_dialog, changePasswordConstraintLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewChangePasswordDialog);
        final AlertDialog alertDialog = builder.create();

        Button changeOldPasswordBtn = viewChangePasswordDialog.findViewById(R.id.changePasswordBtn);
        changeOldPasswordBtn.setOnClickListener(v -> alertDialog.dismiss());

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

        changeImgBtn.setOnClickListener(v ->
                // Launch the photo picker and let the user choose only images
                singlePhotoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        initializeEditTextEditProfileDialog(viewEditProfileDialog);

        Button editOldProfileBtn = viewEditProfileDialog.findViewById(R.id.editProfileBtn);
        editOldProfileBtn.setOnClickListener(v -> {
            // TODO Aggiornare User sul DB (Nota: per ottenere uno oggeto DietiUser con nuovi dati del DietiUser, basta usare il metodo getNewDietiUser())
            updateLocalDietiUser();
            setTextViewWithLocalDietiUserData();
            alertDialog.dismiss();
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    /**
     * This method sets the various TextViews in the fragment with the user's local data. If some data is missing, a message "Complete your profile!" is displayed.
     */
    private void setTextViewWithLocalDietiUserData() {
        userFullNameText.setText(String.format("%s %s", localDietiUser.getName(), localDietiUser.getSurname()));
        userEmailText.setText(localDietiUser.getEmail());

        if (localDietiUser.getGeographicalArea().isEmpty() || localDietiUser.getBiography().isEmpty() || localDietiUser.getLinks().isEmpty()) {
            messageCompleteYourProfileText.setVisibility(View.VISIBLE);

            titleSectionBiography.setVisibility(View.GONE);
            titleSectionLinks.setVisibility(View.GONE);

            geographicalAreaText.setVisibility(View.GONE);
            biographyText.setVisibility(View.GONE);
            linksText.setVisibility(View.GONE);
        } else {
            messageCompleteYourProfileText.setVisibility(View.GONE);

            geographicalAreaText.setText(localDietiUser.getGeographicalArea());
            biographyText.setText(localDietiUser.getBiography());

            StringBuilder links = new StringBuilder();
            for (String link : localDietiUser.getLinks()) {
                links.append(link);
            }
            linksText.setText(links);
        }
    }

    /**
     * This method fills the EditTexts for profile editing with the user's local data
     *
     * @param viewEditProfileDialog reference to the EditProfileDialog
     */
    private void initializeEditTextEditProfileDialog(View viewEditProfileDialog) {
        inputNameEditText = viewEditProfileDialog.findViewById(R.id.inputName);
        inputNameEditText.setText(localDietiUser.getName());

        inputSurnameEditText = viewEditProfileDialog.findViewById(R.id.inputSurname);
        inputSurnameEditText.setText(localDietiUser.getSurname());

        inputGeographicalAreaEditText = viewEditProfileDialog.findViewById(R.id.inputGeographicalArea);
        if (!localDietiUser.getGeographicalArea().isEmpty())
            inputGeographicalAreaEditText.setText(localDietiUser.getGeographicalArea());

        inputBiographyEditText = viewEditProfileDialog.findViewById(R.id.inputBiography);
        if (!localDietiUser.getBiography().isEmpty())
            inputBiographyEditText.setText(localDietiUser.getBiography());

        inputLinksEditText = viewEditProfileDialog.findViewById(R.id.inputLinks);
        if (!localDietiUser.getLinks().isEmpty())
            inputLinksEditText.setText(localDietiUser.getLinks().toString());
    }

    /**
     * This method returns a DietiUser object with the updated data
     */
    private DietiUser getNewDietiUser() {
        String name = inputNameEditText.getText().toString();
        String surname = inputSurnameEditText.getText().toString();
        String biography = inputBiographyEditText.getText().toString();
        String linksStr = inputLinksEditText.getText().toString();
        List<String> links = Arrays.asList(linksStr.split(","));
        String geographicalArea = inputGeographicalAreaEditText.getText().toString();

        return new DietiUser(localDietiUser.getId(), name, surname, localDietiUser.getEmail(), biography, links, geographicalArea);
    }

    /**
     * This method updates the local Dieti User
     */
    private void updateLocalDietiUser() {
        LocalDietiUser.setLocalDietiUser(getActivity(), getNewDietiUser());
    }
}