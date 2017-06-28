/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.swing.support.fatcowicons;

import griffon.plugins.fatcowicons.Fatcow;

import javax.annotation.Nonnull;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.net.URL;

import static griffon.plugins.fatcowicons.Fatcow.invalidDescription;
import static griffon.plugins.fatcowicons.Fatcow.requireValidSize;
import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public class FatcowIcon extends ImageIcon {
    private static final String ERROR_FATCOW_NULL = "Argument 'fatcow' must not be null";
    private Fatcow fatcow;
    private int size;

    public FatcowIcon() {
        this(Fatcow.findByDescription("star:16"));
    }

    public FatcowIcon(@Nonnull Fatcow fatcow) {
        this(fatcow, 16);
    }

    public FatcowIcon(@Nonnull Fatcow fatcow, int size) {
        super(toURL(fatcow, size));
        this.fatcow = requireNonNull(fatcow, ERROR_FATCOW_NULL);
        this.size = size;
    }

    public FatcowIcon(@Nonnull String description) {
        this(Fatcow.findByDescription(description));
        setFatcow(description);
    }

    @Nonnull
    private static URL toURL(@Nonnull Fatcow fatcow, int size) {
        requireNonNull(fatcow, ERROR_FATCOW_NULL);
        String resource = fatcow.asResource(size);
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) {
            throw new IllegalArgumentException("Icon " + fatcow + " does not exist");
        }
        return url;
    }

    @Nonnull
    public Fatcow getFatcow() {
        return fatcow;
    }

    public void setFatcow(@Nonnull Fatcow fatcow) {
        this.fatcow = requireNonNull(fatcow, ERROR_FATCOW_NULL);
        setImage(Toolkit.getDefaultToolkit().getImage(toURL(fatcow, size)));
    }

    public void setFatcow(@Nonnull String description) {
        requireNonBlank(description, "Argument 'description' must not be blank");

        String[] parts = description.split(":");
        if (parts.length == 2) {
            try {
                int s = Integer.parseInt(parts[1]);
                size = requireValidSize(s);
            } catch (NumberFormatException e) {
                throw invalidDescription(description, e);
            }
        } else if (size == 0) {
            size = 16;
        }

        fatcow = Fatcow.findByDescription(description);
        setImage(Toolkit.getDefaultToolkit().getImage(toURL(fatcow, size)));
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = requireValidSize(size);
        setImage(Toolkit.getDefaultToolkit().getImage(toURL(fatcow, size)));
    }
}
