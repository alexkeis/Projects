package spire.example.cmt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Term extends Activity{
	TextView txt;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        txt = (TextView) findViewById(R.id.textView_terms);
        String text="I We authorize CMT to act as my our agent to organize the hire of a replacement vehicle for " +
        		"my our use while my vehicle is being repaired replaced. I we also authorize CMT " +
        		"to act to recover the costs of the hire of that replacement vehicle, and if " +
        		"necessary to instruct solicitors to act in my our name to recover such hire costs, " +
        		"including commencing legal proceedings if required. The hirer acknowledges and agrees " +
        		"that: CMT may initially pay the hire costs on my our behalf. In that event that CMT does " +
        		"not recover such costs the hirer is liable to reimburse CMT for that expense. CMT discloses " +
        		"and hirer acknowledges that CMT may receive a commission on the hire costs from the car hire company.";
        txt.setText(text);
        
    }

}
