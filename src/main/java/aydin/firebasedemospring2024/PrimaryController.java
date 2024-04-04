package aydin.firebasedemospring2024;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {
    @FXML
    private Label errorMess;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Button signinBtn;

    private static String useForSignin;

    void initialize() {
        useForSignin = "";
    }

    public static String getUserSignin(){
        return useForSignin;
    }

    @FXML
    void onSigninClick(ActionEvent event) throws IOException {

        try {
            String userEmailInput = nameTextField.getText();
            UserRecord userRecord = DemoApp.fauth.getUserByEmail(userEmailInput);
            DemoApp.setRoot("secondary");
            System.out.println("Your account is authethicated.");
            useForSignin = userEmailInput;
            DemoApp.setRoot("secondary");
        } catch (Exception e) {
            errorMess.getStyleClass().clear();
            errorMess.getStyleClass().add("err-mess");
            errorMess.setText("Error Authenticating Sign in. Check spelling for sign in account \"user222@example.com\"");
        }

    }


    @FXML
    void registerButtonClicked(ActionEvent event) {
        registerUser();
    }



    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user222@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
            + " check Firebase > Authentication > Users tab");
            errorMess.setText("Account has been create. Sign in with email \"user222@example.com\"");
            errorMess.getStyleClass().clear();
            errorMess.getStyleClass().add("acc-exist");

            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        }

    }

}