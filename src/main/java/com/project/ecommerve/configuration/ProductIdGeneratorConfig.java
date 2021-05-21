package com.project.ecommerve.configuration;

import java.io.Serializable;
import java.util.Properties;

import lombok.NoArgsConstructor;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class ProductIdGeneratorConfig extends SequenceStyleGenerator {

  public static final String PREFIX_FORMAT = "value_prefix";
  public static final String PREFIX_DEFAULT = "";
  private String valuePrefix;

  public static final String NUMBER_FORMAT = "number_format";
  public static final String NUMBER_DEFAULT = "%d";
  private String numberFormat;

  @Override
  public void configure(
      final Type type, final Properties params, final ServiceRegistry serviceRegistry)
      throws MappingException {
    super.configure(LongType.INSTANCE, params, serviceRegistry);
    valuePrefix = ConfigurationHelper.getString(PREFIX_FORMAT, params, PREFIX_DEFAULT);
    numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT, params, NUMBER_DEFAULT);
  }

  @Override
  public Serializable generate(final SharedSessionContractImplementor session, final Object object)
      throws HibernateException {
    return valuePrefix + String.format(numberFormat, super.generate(session, object));
  }
}
