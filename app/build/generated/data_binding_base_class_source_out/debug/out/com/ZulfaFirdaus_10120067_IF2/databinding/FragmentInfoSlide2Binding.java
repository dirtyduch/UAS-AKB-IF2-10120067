// Generated by view binder compiler. Do not edit!
package com.ZulfaFirdaus_10120067_IF2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ZulfaFirdaus_10120067_IF2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentInfoSlide2Binding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout layoutTitle;

  @NonNull
  public final TextView navigationInfo2;

  @NonNull
  public final TextView titleInfo2;

  private FragmentInfoSlide2Binding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout layoutTitle, @NonNull TextView navigationInfo2,
      @NonNull TextView titleInfo2) {
    this.rootView = rootView;
    this.layoutTitle = layoutTitle;
    this.navigationInfo2 = navigationInfo2;
    this.titleInfo2 = titleInfo2;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentInfoSlide2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentInfoSlide2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_info_slide2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentInfoSlide2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.layout_title;
      RelativeLayout layoutTitle = ViewBindings.findChildViewById(rootView, id);
      if (layoutTitle == null) {
        break missingId;
      }

      id = R.id.navigation_info2;
      TextView navigationInfo2 = ViewBindings.findChildViewById(rootView, id);
      if (navigationInfo2 == null) {
        break missingId;
      }

      id = R.id.title_info_2;
      TextView titleInfo2 = ViewBindings.findChildViewById(rootView, id);
      if (titleInfo2 == null) {
        break missingId;
      }

      return new FragmentInfoSlide2Binding((RelativeLayout) rootView, layoutTitle, navigationInfo2,
          titleInfo2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
