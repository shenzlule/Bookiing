package org.project.booking.ui.utils;

import com.google.firebase.firestore.FirebaseFirestore;

import org.project.booking.ui.models.Item;

public class FirebaseHelper {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void uploadItem(Item item, Runnable onSuccess, Runnable onFailure) {
        db.collection("items").add(item)
                .addOnSuccessListener(documentReference -> onSuccess.run())
                .addOnFailureListener(e -> onFailure.run());
    }
}
