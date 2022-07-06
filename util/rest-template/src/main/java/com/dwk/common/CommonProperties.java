package com.dwk.common;

import com.dwk.properties.GetProperties;
import com.dwk.properties.PostProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CommonProperties{

    private GetProperties getProperties = new GetProperties();

    private PostProperties postProperties = new PostProperties();

}
