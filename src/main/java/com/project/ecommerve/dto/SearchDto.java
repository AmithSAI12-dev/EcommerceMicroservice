package com.project.ecommerve.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchDto {

  private List<String> categories = new ArrayList<>();
  private List<String> brands = new ArrayList<>();
  private float start;
  private float end;
}
