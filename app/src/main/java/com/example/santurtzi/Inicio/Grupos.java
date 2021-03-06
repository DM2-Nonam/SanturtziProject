package com.example.santurtzi.Inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.santurtzi.Grupo.AdaptadorGrupo;
import com.example.santurtzi.Grupo.DialogoGrupos;
import com.example.santurtzi.Grupo.Grupo;
import com.example.santurtzi.Grupo.GrupoDao;
import com.example.santurtzi.R;

import java.util.ArrayList;

public class Grupos extends AppCompatActivity implements DialogoGrupos.OnDialogoConfirmacionListener {

    private AdaptadorGrupo ag;
    private GrupoDao grupoDao;
    private ArrayList<Grupo> grupos;
    private ListView lstGrupos;
    private DialogoGrupos dg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        grupoDao=new GrupoDao(this.getBaseContext(),"Grupo",null,1);
        grupos=grupoDao.verGrupos();
        ag= new AdaptadorGrupo(this, this.grupos);
        lstGrupos=findViewById(R.id.lvGrupos);
        lstGrupos.setAdapter(ag);

        generarEventos();
    }

    private void generarEventos()
    {
        lstGrupos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Grupo g= (Grupo) lstGrupos.getItemAtPosition(i);
                int h=ag.getH();
                switch (h)
                {
                    case 1:
                        Log.i("El niño ha seleccionado el grupo ",g.getNomGrupo());
                        ag.ver();
                        break;
                    case 2:
                        FragmentManager fragmentManager= getSupportFragmentManager();
                        dg= new DialogoGrupos(g);
                        dg.show(fragmentManager, "Dialogo para editar grupo");
                        break;
                    case 3:
                        grupoDao.eliminarGrupo(g);
                        Toast.makeText(Grupos.this.getBaseContext(), "Ahora solo podras ver los grupos", Toast.LENGTH_SHORT).show();
                        ag.ver();
                        break;
                }
                grupos=grupoDao.verGrupos();
                ag.refrescarLista(grupos);
            }
        });
    }

    public void crearGrupo(View v)
    {
        FragmentManager fragmentManager= getSupportFragmentManager();
        DialogoGrupos dg= new DialogoGrupos();
        dg.show(fragmentManager, "Dialogo para añadir grupos");

    }

    @Override
    public void onPossitiveButtonClick(Grupo g) {
        if(ag.getH()==1)
        {
            if(grupoDao.aniadirGrupo(g))
            {
                //Podria gestionar esto en el dialogo quedaria mas txatxi
                Toast.makeText(this.getBaseContext(),"Grupo "+g.getNomGrupo()+" añadido", Toast.LENGTH_SHORT).show();
                grupos=grupoDao.verGrupos();
                ag.refrescarLista(grupos);
            }
            else
            {
                Toast.makeText(this.getBaseContext(),"Grupo "+g.getNomGrupo()+" ya existe", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            grupoDao.editarGrupo(g);
            grupos=grupoDao.verGrupos();
            ag.refrescarLista(grupos);
            Toast.makeText(this.getBaseContext(),"Grupo "+g.getNomGrupo()+" editado", Toast.LENGTH_SHORT).show();
        }
        ag.ver();
    }

    @Override
    public void onNegativeButtonClick() {
        if(ag.getH()==1)
            Toast.makeText(this.getBaseContext(),"No has añadido ningun grupo", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Grupos.this.getBaseContext(), "No has editado el grupo", Toast.LENGTH_SHORT).show();
        Toast.makeText(Grupos.this.getBaseContext(), "Ahora solo podras ver los grupos", Toast.LENGTH_SHORT).show();
    }

    public void borrarGrupos(View v)
    {
        ag.borrar();
        Toast.makeText(this.getBaseContext(),"Ahora el grupo que selecciones se borrara", Toast.LENGTH_SHORT).show();
    }

    public void editarGrupo(View v)
    {
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        ag.editar();
        Toast.makeText(this.getBaseContext(),"Ahora podras editar el numero de integrantes del grupo que selecciones", Toast.LENGTH_SHORT).show();
    }

}