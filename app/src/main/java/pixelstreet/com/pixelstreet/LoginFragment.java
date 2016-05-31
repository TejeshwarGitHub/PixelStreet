package pixelstreet.com.pixelstreet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    ImageView text;
    int index;
/*

    public static LoginFragment getInstance(int position){
        LoginFragment loginFragment=new LoginFragment();
        Bundle args=new Bundle();
        args.putInt("position",position);
        loginFragment.setArguments(args);
        return loginFragment;
    }
*/

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        index = getArguments().getInt("index", 0);
        view.setTag(index);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = (ImageView) view.findViewById(R.id.loginText);

        switch (index) {
            case 0:
                text.setImageResource(R.drawable.bird);
                break;
            case 1:
                text.setImageResource(R.drawable.boygirl);
                break;
            case 2:
                text.setImageResource(R.drawable.potter);
                break;
            case 3:
                text.setImageResource(R.drawable.whirl);
                break;
        }
    }
}
