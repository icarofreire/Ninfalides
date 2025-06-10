package org.crawler;

import java.util.ArrayList;
import java.util.List;

/**
 * author ;
 */
public class StoredDocument {

  private List<StoreField> storeFields;
  
  public StoredDocument(){
	  this.storeFields = new ArrayList<>();
  }

  public StoredDocument(List<StoreField> storeFields) {
    this.storeFields = storeFields;
  }

  public List<StoreField> getStoreFields() {
    return storeFields;
  }
}
