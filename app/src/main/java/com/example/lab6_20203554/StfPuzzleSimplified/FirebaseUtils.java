package com.example.lab6_20203554.StfPuzzleSimplified;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtils {
    /*
    public static String getusuarioActualId(){
        //devuelvo el id de autenticacion del usuario logeado
        return FirebaseAuth.getInstance().getUid();
    }

    public static DocumentReference getUsuarioActualDetalles(){
        return FirebaseFirestore.getInstance().collection("alumnos").document(FirebaseUtilDg.getusuarioActualId());
    }

    public static CollectionReference getCollAlumnos(){
        return FirebaseFirestore.getInstance().collection("alumnos");
    }
    */
    public static StorageReference getActualRef(String user){
        return FirebaseStorage.getInstance().getReference().child(user);
    }

}
