/*
 * Copyright 2018 Vandolf Estrellado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mvp.rui.androidmvpdemo.common.dagger.modules;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mvp.rui.androidmvpdemo.common.dagger.scopes.FragmentScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Provides base fragment dependencies. This must be included in all fragment modules, which must
 * provide a concrete implementation of {@link Fragment} and named {@link #FRAGMENT}.
 */
@Module
public abstract class BaseFragmentModule {

    /**
     * See {@link BaseChildFragmentModule} class documentation regarding the need for this name.
     */
    public static final String FRAGMENT = "BaseFragmentModule.fragment";

    public static final String CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager";

    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @FragmentScope
    static FragmentManager childFragmentManager(@Named(FRAGMENT) Fragment fragment) {
        return fragment.getChildFragmentManager();
    }

}
