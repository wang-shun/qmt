/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbc.api.dto;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-2-27")
public class TSimpleTextLive implements org.apache.thrift.TBase<TSimpleTextLive, TSimpleTextLive._Fields>, java.io.Serializable, Cloneable, Comparable<TSimpleTextLive> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSimpleTextLive");

  private static final org.apache.thrift.protocol.TField TEXT_LIVE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("textLiveId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TSimpleTextLiveStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TSimpleTextLiveTupleSchemeFactory());
  }

  private long textLiveId; // required
  private com.lesports.qmt.tlive.api.common.TextLiveType type; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TEXT_LIVE_ID((short)1, "textLiveId"),
    /**
     * 
     * @see com.lesports.qmt.tlive.api.common.TextLiveType
     */
    TYPE((short)2, "type");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TEXT_LIVE_ID
          return TEXT_LIVE_ID;
        case 2: // TYPE
          return TYPE;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TEXTLIVEID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TEXT_LIVE_ID, new org.apache.thrift.meta_data.FieldMetaData("textLiveId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.lesports.qmt.tlive.api.common.TextLiveType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSimpleTextLive.class, metaDataMap);
  }

  public TSimpleTextLive() {
  }

  public TSimpleTextLive(
    long textLiveId)
  {
    this();
    this.textLiveId = textLiveId;
    setTextLiveIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSimpleTextLive(TSimpleTextLive other) {
    __isset_bitfield = other.__isset_bitfield;
    this.textLiveId = other.textLiveId;
    if (other.isSetType()) {
      this.type = other.type;
    }
  }

  public TSimpleTextLive deepCopy() {
    return new TSimpleTextLive(this);
  }

  @Override
  public void clear() {
    setTextLiveIdIsSet(false);
    this.textLiveId = 0;
    this.type = null;
  }

  public long getTextLiveId() {
    return this.textLiveId;
  }

  public void setTextLiveId(long textLiveId) {
    this.textLiveId = textLiveId;
    setTextLiveIdIsSet(true);
  }

  public void unsetTextLiveId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TEXTLIVEID_ISSET_ID);
  }

  /** Returns true if field textLiveId is set (has been assigned a value) and false otherwise */
  public boolean isSetTextLiveId() {
    return EncodingUtils.testBit(__isset_bitfield, __TEXTLIVEID_ISSET_ID);
  }

  public void setTextLiveIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TEXTLIVEID_ISSET_ID, value);
  }

  /**
   * 
   * @see com.lesports.qmt.tlive.api.common.TextLiveType
   */
  public com.lesports.qmt.tlive.api.common.TextLiveType getType() {
    return this.type;
  }

  /**
   * 
   * @see com.lesports.qmt.tlive.api.common.TextLiveType
   */
  public void setType(com.lesports.qmt.tlive.api.common.TextLiveType type) {
    this.type = type;
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TEXT_LIVE_ID:
      if (value == null) {
        unsetTextLiveId();
      } else {
        setTextLiveId((Long)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((com.lesports.qmt.tlive.api.common.TextLiveType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TEXT_LIVE_ID:
      return Long.valueOf(getTextLiveId());

    case TYPE:
      return getType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TEXT_LIVE_ID:
      return isSetTextLiveId();
    case TYPE:
      return isSetType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TSimpleTextLive)
      return this.equals((TSimpleTextLive)that);
    return false;
  }

  public boolean equals(TSimpleTextLive that) {
    if (that == null)
      return false;

    boolean this_present_textLiveId = true;
    boolean that_present_textLiveId = true;
    if (this_present_textLiveId || that_present_textLiveId) {
      if (!(this_present_textLiveId && that_present_textLiveId))
        return false;
      if (this.textLiveId != that.textLiveId)
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_textLiveId = true;
    list.add(present_textLiveId);
    if (present_textLiveId)
      list.add(textLiveId);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type.getValue());

    return list.hashCode();
  }

  @Override
  public int compareTo(TSimpleTextLive other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTextLiveId()).compareTo(other.isSetTextLiveId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTextLiveId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.textLiveId, other.textLiveId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TSimpleTextLive(");
    boolean first = true;

    sb.append("textLiveId:");
    sb.append(this.textLiveId);
    first = false;
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
      }
      first = false;
    }
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TSimpleTextLiveStandardSchemeFactory implements SchemeFactory {
    public TSimpleTextLiveStandardScheme getScheme() {
      return new TSimpleTextLiveStandardScheme();
    }
  }

  private static class TSimpleTextLiveStandardScheme extends StandardScheme<TSimpleTextLive> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSimpleTextLive struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TEXT_LIVE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.textLiveId = iprot.readI64();
              struct.setTextLiveIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = com.lesports.qmt.tlive.api.common.TextLiveType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSimpleTextLive struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TEXT_LIVE_ID_FIELD_DESC);
      oprot.writeI64(struct.textLiveId);
      oprot.writeFieldEnd();
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeI32(struct.type.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TSimpleTextLiveTupleSchemeFactory implements SchemeFactory {
    public TSimpleTextLiveTupleScheme getScheme() {
      return new TSimpleTextLiveTupleScheme();
    }
  }

  private static class TSimpleTextLiveTupleScheme extends TupleScheme<TSimpleTextLive> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSimpleTextLive struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTextLiveId()) {
        optionals.set(0);
      }
      if (struct.isSetType()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTextLiveId()) {
        oprot.writeI64(struct.textLiveId);
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSimpleTextLive struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.textLiveId = iprot.readI64();
        struct.setTextLiveIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.type = com.lesports.qmt.tlive.api.common.TextLiveType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
    }
  }

}

