/**
 * Autogenerated by Thrift Compiler (0.14.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package mar.analysis.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.14.0)", date = "2022-02-23")
public class ValidationJob implements org.apache.thrift.TBase<ValidationJob, ValidationJob._Fields>, java.io.Serializable, Cloneable, Comparable<ValidationJob> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ValidationJob");

  private static final org.apache.thrift.protocol.TField MODEL_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("modelId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RELATIVE_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("relative_path", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField FULL_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("full_path", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField OPTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("options", org.apache.thrift.protocol.TType.MAP, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ValidationJobStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ValidationJobTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String modelId; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String relative_path; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String full_path; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String type; // required
  public @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.String> options; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MODEL_ID((short)1, "modelId"),
    RELATIVE_PATH((short)2, "relative_path"),
    FULL_PATH((short)3, "full_path"),
    TYPE((short)4, "type"),
    OPTIONS((short)5, "options");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MODEL_ID
          return MODEL_ID;
        case 2: // RELATIVE_PATH
          return RELATIVE_PATH;
        case 3: // FULL_PATH
          return FULL_PATH;
        case 4: // TYPE
          return TYPE;
        case 5: // OPTIONS
          return OPTIONS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MODEL_ID, new org.apache.thrift.meta_data.FieldMetaData("modelId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RELATIVE_PATH, new org.apache.thrift.meta_data.FieldMetaData("relative_path", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FULL_PATH, new org.apache.thrift.meta_data.FieldMetaData("full_path", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPTIONS, new org.apache.thrift.meta_data.FieldMetaData("options", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ValidationJob.class, metaDataMap);
  }

  public ValidationJob() {
  }

  public ValidationJob(
    java.lang.String modelId,
    java.lang.String relative_path,
    java.lang.String full_path,
    java.lang.String type,
    java.util.Map<java.lang.String,java.lang.String> options)
  {
    this();
    this.modelId = modelId;
    this.relative_path = relative_path;
    this.full_path = full_path;
    this.type = type;
    this.options = options;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ValidationJob(ValidationJob other) {
    if (other.isSetModelId()) {
      this.modelId = other.modelId;
    }
    if (other.isSetRelative_path()) {
      this.relative_path = other.relative_path;
    }
    if (other.isSetFull_path()) {
      this.full_path = other.full_path;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetOptions()) {
      java.util.Map<java.lang.String,java.lang.String> __this__options = new java.util.HashMap<java.lang.String,java.lang.String>(other.options);
      this.options = __this__options;
    }
  }

  public ValidationJob deepCopy() {
    return new ValidationJob(this);
  }

  @Override
  public void clear() {
    this.modelId = null;
    this.relative_path = null;
    this.full_path = null;
    this.type = null;
    this.options = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getModelId() {
    return this.modelId;
  }

  public ValidationJob setModelId(@org.apache.thrift.annotation.Nullable java.lang.String modelId) {
    this.modelId = modelId;
    return this;
  }

  public void unsetModelId() {
    this.modelId = null;
  }

  /** Returns true if field modelId is set (has been assigned a value) and false otherwise */
  public boolean isSetModelId() {
    return this.modelId != null;
  }

  public void setModelIdIsSet(boolean value) {
    if (!value) {
      this.modelId = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getRelative_path() {
    return this.relative_path;
  }

  public ValidationJob setRelative_path(@org.apache.thrift.annotation.Nullable java.lang.String relative_path) {
    this.relative_path = relative_path;
    return this;
  }

  public void unsetRelative_path() {
    this.relative_path = null;
  }

  /** Returns true if field relative_path is set (has been assigned a value) and false otherwise */
  public boolean isSetRelative_path() {
    return this.relative_path != null;
  }

  public void setRelative_pathIsSet(boolean value) {
    if (!value) {
      this.relative_path = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getFull_path() {
    return this.full_path;
  }

  public ValidationJob setFull_path(@org.apache.thrift.annotation.Nullable java.lang.String full_path) {
    this.full_path = full_path;
    return this;
  }

  public void unsetFull_path() {
    this.full_path = null;
  }

  /** Returns true if field full_path is set (has been assigned a value) and false otherwise */
  public boolean isSetFull_path() {
    return this.full_path != null;
  }

  public void setFull_pathIsSet(boolean value) {
    if (!value) {
      this.full_path = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getType() {
    return this.type;
  }

  public ValidationJob setType(@org.apache.thrift.annotation.Nullable java.lang.String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public int getOptionsSize() {
    return (this.options == null) ? 0 : this.options.size();
  }

  public void putToOptions(java.lang.String key, java.lang.String val) {
    if (this.options == null) {
      this.options = new java.util.HashMap<java.lang.String,java.lang.String>();
    }
    this.options.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.String,java.lang.String> getOptions() {
    return this.options;
  }

  public ValidationJob setOptions(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.String> options) {
    this.options = options;
    return this;
  }

  public void unsetOptions() {
    this.options = null;
  }

  /** Returns true if field options is set (has been assigned a value) and false otherwise */
  public boolean isSetOptions() {
    return this.options != null;
  }

  public void setOptionsIsSet(boolean value) {
    if (!value) {
      this.options = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case MODEL_ID:
      if (value == null) {
        unsetModelId();
      } else {
        setModelId((java.lang.String)value);
      }
      break;

    case RELATIVE_PATH:
      if (value == null) {
        unsetRelative_path();
      } else {
        setRelative_path((java.lang.String)value);
      }
      break;

    case FULL_PATH:
      if (value == null) {
        unsetFull_path();
      } else {
        setFull_path((java.lang.String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((java.lang.String)value);
      }
      break;

    case OPTIONS:
      if (value == null) {
        unsetOptions();
      } else {
        setOptions((java.util.Map<java.lang.String,java.lang.String>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MODEL_ID:
      return getModelId();

    case RELATIVE_PATH:
      return getRelative_path();

    case FULL_PATH:
      return getFull_path();

    case TYPE:
      return getType();

    case OPTIONS:
      return getOptions();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MODEL_ID:
      return isSetModelId();
    case RELATIVE_PATH:
      return isSetRelative_path();
    case FULL_PATH:
      return isSetFull_path();
    case TYPE:
      return isSetType();
    case OPTIONS:
      return isSetOptions();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof ValidationJob)
      return this.equals((ValidationJob)that);
    return false;
  }

  public boolean equals(ValidationJob that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_modelId = true && this.isSetModelId();
    boolean that_present_modelId = true && that.isSetModelId();
    if (this_present_modelId || that_present_modelId) {
      if (!(this_present_modelId && that_present_modelId))
        return false;
      if (!this.modelId.equals(that.modelId))
        return false;
    }

    boolean this_present_relative_path = true && this.isSetRelative_path();
    boolean that_present_relative_path = true && that.isSetRelative_path();
    if (this_present_relative_path || that_present_relative_path) {
      if (!(this_present_relative_path && that_present_relative_path))
        return false;
      if (!this.relative_path.equals(that.relative_path))
        return false;
    }

    boolean this_present_full_path = true && this.isSetFull_path();
    boolean that_present_full_path = true && that.isSetFull_path();
    if (this_present_full_path || that_present_full_path) {
      if (!(this_present_full_path && that_present_full_path))
        return false;
      if (!this.full_path.equals(that.full_path))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_options = true && this.isSetOptions();
    boolean that_present_options = true && that.isSetOptions();
    if (this_present_options || that_present_options) {
      if (!(this_present_options && that_present_options))
        return false;
      if (!this.options.equals(that.options))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetModelId()) ? 131071 : 524287);
    if (isSetModelId())
      hashCode = hashCode * 8191 + modelId.hashCode();

    hashCode = hashCode * 8191 + ((isSetRelative_path()) ? 131071 : 524287);
    if (isSetRelative_path())
      hashCode = hashCode * 8191 + relative_path.hashCode();

    hashCode = hashCode * 8191 + ((isSetFull_path()) ? 131071 : 524287);
    if (isSetFull_path())
      hashCode = hashCode * 8191 + full_path.hashCode();

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + type.hashCode();

    hashCode = hashCode * 8191 + ((isSetOptions()) ? 131071 : 524287);
    if (isSetOptions())
      hashCode = hashCode * 8191 + options.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ValidationJob other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetModelId(), other.isSetModelId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModelId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.modelId, other.modelId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetRelative_path(), other.isSetRelative_path());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRelative_path()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.relative_path, other.relative_path);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetFull_path(), other.isSetFull_path());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFull_path()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.full_path, other.full_path);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetType(), other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetOptions(), other.isSetOptions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOptions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.options, other.options);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ValidationJob(");
    boolean first = true;

    sb.append("modelId:");
    if (this.modelId == null) {
      sb.append("null");
    } else {
      sb.append(this.modelId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("relative_path:");
    if (this.relative_path == null) {
      sb.append("null");
    } else {
      sb.append(this.relative_path);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("full_path:");
    if (this.full_path == null) {
      sb.append("null");
    } else {
      sb.append(this.full_path);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("options:");
    if (this.options == null) {
      sb.append("null");
    } else {
      sb.append(this.options);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ValidationJobStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ValidationJobStandardScheme getScheme() {
      return new ValidationJobStandardScheme();
    }
  }

  private static class ValidationJobStandardScheme extends org.apache.thrift.scheme.StandardScheme<ValidationJob> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ValidationJob struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MODEL_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.modelId = iprot.readString();
              struct.setModelIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RELATIVE_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.relative_path = iprot.readString();
              struct.setRelative_pathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FULL_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.full_path = iprot.readString();
              struct.setFull_pathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // OPTIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                struct.options = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map0.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _key1;
                @org.apache.thrift.annotation.Nullable java.lang.String _val2;
                for (int _i3 = 0; _i3 < _map0.size; ++_i3)
                {
                  _key1 = iprot.readString();
                  _val2 = iprot.readString();
                  struct.options.put(_key1, _val2);
                }
                iprot.readMapEnd();
              }
              struct.setOptionsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ValidationJob struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.modelId != null) {
        oprot.writeFieldBegin(MODEL_ID_FIELD_DESC);
        oprot.writeString(struct.modelId);
        oprot.writeFieldEnd();
      }
      if (struct.relative_path != null) {
        oprot.writeFieldBegin(RELATIVE_PATH_FIELD_DESC);
        oprot.writeString(struct.relative_path);
        oprot.writeFieldEnd();
      }
      if (struct.full_path != null) {
        oprot.writeFieldBegin(FULL_PATH_FIELD_DESC);
        oprot.writeString(struct.full_path);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      if (struct.options != null) {
        oprot.writeFieldBegin(OPTIONS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.options.size()));
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter4 : struct.options.entrySet())
          {
            oprot.writeString(_iter4.getKey());
            oprot.writeString(_iter4.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ValidationJobTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ValidationJobTupleScheme getScheme() {
      return new ValidationJobTupleScheme();
    }
  }

  private static class ValidationJobTupleScheme extends org.apache.thrift.scheme.TupleScheme<ValidationJob> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ValidationJob struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetModelId()) {
        optionals.set(0);
      }
      if (struct.isSetRelative_path()) {
        optionals.set(1);
      }
      if (struct.isSetFull_path()) {
        optionals.set(2);
      }
      if (struct.isSetType()) {
        optionals.set(3);
      }
      if (struct.isSetOptions()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetModelId()) {
        oprot.writeString(struct.modelId);
      }
      if (struct.isSetRelative_path()) {
        oprot.writeString(struct.relative_path);
      }
      if (struct.isSetFull_path()) {
        oprot.writeString(struct.full_path);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetOptions()) {
        {
          oprot.writeI32(struct.options.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter5 : struct.options.entrySet())
          {
            oprot.writeString(_iter5.getKey());
            oprot.writeString(_iter5.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ValidationJob struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.modelId = iprot.readString();
        struct.setModelIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.relative_path = iprot.readString();
        struct.setRelative_pathIsSet(true);
      }
      if (incoming.get(2)) {
        struct.full_path = iprot.readString();
        struct.setFull_pathIsSet(true);
      }
      if (incoming.get(3)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TMap _map6 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.options = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map6.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _key7;
          @org.apache.thrift.annotation.Nullable java.lang.String _val8;
          for (int _i9 = 0; _i9 < _map6.size; ++_i9)
          {
            _key7 = iprot.readString();
            _val8 = iprot.readString();
            struct.options.put(_key7, _val8);
          }
        }
        struct.setOptionsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

