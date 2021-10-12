package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSaveBinding;

import ViewModel.Motion.accelerometerViewModel;
import ViewModel.Motion.gpsLocationViewModel;
import ViewModel.Motion.gravityViewModel;
import ViewModel.Motion.gyroscopeViewModel;
import ViewModel.Motion.motionViewModel;
import ViewModel.Motion.rotationViewModel;
import ViewModel.sensorViewModel;
import ViewModel.Motion.stepViewModel;

public class SaveFragment extends Fragment {
    private FragmentSaveBinding saveBinding;
    private accelerometerViewModel accViewModel;
    private gravityViewModel gravViewModel;
    private gyroscopeViewModel gyroViewModel;
    private motionViewModel motViewModel;
    private rotationViewModel rotViewModel;
    private stepViewModel stViewModel;
    private gpsLocationViewModel gpsViewModel;
    private sensorViewModel svViewModel;
    public SaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        saveBinding = FragmentSaveBinding.inflate(inflater, container, false);
        return saveBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        svViewModel = new ViewModelProvider(requireActivity()).get(sensorViewModel.class);
        accViewModel = new ViewModelProvider(requireActivity()).get(accelerometerViewModel.class);
        //String Title = "Save " + svViewModel.getSensor() + " File Page";
        //saveBinding.svTitleTV.setText(Title);
        saveBinding.shareButton.setVisibility(View.INVISIBLE);
        /*String texto = "";
        for(int i = 0; i<accViewModel.getAccelerometer().size(); i++){
            String ax = String.valueOf(accViewModel.getAccelerometer().get(i).getAx());
            String ay = String.valueOf(accViewModel.getAccelerometer().get(i).getAy());
            String az = String.valueOf(accViewModel.getAccelerometer().get(i).getAz());
            String dataTime = accViewModel.getAccelerometer().get(i).getDateTime();
            texto = texto + "Ax: " + ax + "; Ay: " + ay + "; Az: " + az + "; Data and Time: " + dataTime + "\n";
        }
        Log.d("Textao: ", texto);*/
        //save();
    }

   /* public void save(){
        saveBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(saveBinding.fileNameET.getText().toString())){
                    Toast.makeText(requireContext(), "Please fill the filename field", Toast.LENGTH_LONG);
                }else{
                    String filename = saveBinding.fileNameET.getText().toString();
                    File file = new File(requireActivity().getExternalFilesDir(null), filename);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        String texto = "";
                        if(svViewModel.getSensor().equals("accelerometer")){
                            for(int i = 0; i<accViewModel.getAccelerometer().size(); i++){
                                String ax = String.valueOf(accViewModel.getAccelerometer().get(i).getAx());
                                String ay = String.valueOf(accViewModel.getAccelerometer().get(i).getAy());
                                String az = String.valueOf(accViewModel.getAccelerometer().get(i).getAz());
                                String dataTime = accViewModel.getAccelerometer().get(i).getDateTime();
                                texto = texto + "Ax: " + ax + "; Ay: " + ay + "; Az: " + az + "; Data and Time: " + dataTime + "\n";
                            }
                            Log.d("Textao: ", texto);

                        }
                        //String texto = "Estou tentando escrever nesse raio de arquivo";
                        //fos.write(texto.getBytes(StandardCharsets.UTF_8));
                        //fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }*/
}