package com.example.noubyte.mego.Models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by Noubyte on 28/03/2017.
 */

public class Users {

    public static final String id_ws ="id";
    public static final String nombre_ws ="nombre";
    public static final String comentario_ws ="comentario";
    public static final String sexo_ws ="sexo";
    public static final String edad_ws ="edad";
    public static final String sincronizado_ws ="sincronizado";
    public static final String diagnostico_ws ="diagnostico";
    public static final String contacto_ws ="contacto";

    @SerializedName(id_ws)
    @DatabaseField(generatedId = true,columnName = id_ws)
    private int id;

    @SerializedName(nombre_ws)
    @DatabaseField(columnName = nombre_ws)
    private String nombre;

    @SerializedName(comentario_ws)
    @DatabaseField(columnName = comentario_ws)
    private String comentario;

    @SerializedName(sexo_ws)
    @DatabaseField(columnName = sexo_ws)
    private String sexo;

    @SerializedName(edad_ws)
    @DatabaseField(columnName = edad_ws)
    private String edad;

    @SerializedName(sincronizado_ws)
    @DatabaseField(columnName = sincronizado_ws)
    private String sincronizado;

    @SerializedName(diagnostico_ws)
    @DatabaseField(columnName = diagnostico_ws)
    private String diagnostico;

    @SerializedName(contacto_ws)
    @DatabaseField(columnName = contacto_ws)
    private String contacto;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setINombre(String nombre){
        this.nombre=nombre;
    }
    public String getComentario(){
        return comentario;
    }

    public void setComentario(String comentario){
        this.comentario=comentario;
    }

    public String getSexo(){
        return sexo;
    }

    public void setSexo(String sexo){
        this.sexo=sexo;
    }

    public String getEdad(){
        return edad;
    }

    public void setEdad(String edad){
        this.edad=edad;
    }

    public String getSincronizado(){
        return sincronizado;
    }

    public void setSincronizado(String sincronizado){
        this.sincronizado=sincronizado;
    }

    public String getDiagnostico(){
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico){
        this.diagnostico=diagnostico;
    }

    public String getContacto(){
        return contacto;
    }

    public void setContacto(String contacto){
        this.contacto=contacto;
    }





}
