package com.project.ecommerve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.project.ecommerve.constants.SearchOperation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

  private String key;
  private Object value;
  private SearchOperation searchOperation;
}
