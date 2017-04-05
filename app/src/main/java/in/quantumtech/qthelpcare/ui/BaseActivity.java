package in.quantumtech.qthelpcare.ui;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.quantumtech.qthelpcare.R;

/**
 * Created by quantum on 31/3/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;

    @Override
    public void onBackPressed() {
        if (isNavOpen()) {
            closeNav();
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 1){
                findViewById(R.id.container).setVisibility(View.GONE);
            }
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_in_right);
        }

    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    protected boolean isNavOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }
    protected void closeNav() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
