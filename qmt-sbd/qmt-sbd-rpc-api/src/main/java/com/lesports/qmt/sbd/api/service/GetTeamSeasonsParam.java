/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbd.api.service;

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
/**
 * 根据条件查询赛事信息
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-12-9")
public class GetTeamSeasonsParam implements org.apache.thrift.TBase<GetTeamSeasonsParam, GetTeamSeasonsParam._Fields>, java.io.Serializable, Cloneable, Comparable<GetTeamSeasonsParam> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetTeamSeasonsParam");

  private static final org.apache.thrift.protocol.TField TID_FIELD_DESC = new org.apache.thrift.protocol.TField("tid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField CSID_FIELD_DESC = new org.apache.thrift.protocol.TField("csid", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetTeamSeasonsParamStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetTeamSeasonsParamTupleSchemeFactory());
  }

  private long tid; // optional
  private long pid; // optional
  private long csid; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TID((short)1, "tid"),
    PID((short)2, "pid"),
    CSID((short)3, "csid");

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
        case 1: // TID
          return TID;
        case 2: // PID
          return PID;
        case 3: // CSID
          return CSID;
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
  private static final int __TID_ISSET_ID = 0;
  private static final int __PID_ISSET_ID = 1;
  private static final int __CSID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TID,_Fields.PID,_Fields.CSID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TID, new org.apache.thrift.meta_data.FieldMetaData("tid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CSID, new org.apache.thrift.meta_data.FieldMetaData("csid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetTeamSeasonsParam.class, metaDataMap);
  }

  public GetTeamSeasonsParam() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetTeamSeasonsParam(GetTeamSeasonsParam other) {
    __isset_bitfield = other.__isset_bitfield;
    this.tid = other.tid;
    this.pid = other.pid;
    this.csid = other.csid;
  }

  public GetTeamSeasonsParam deepCopy() {
    return new GetTeamSeasonsParam(this);
  }

  @Override
  public void clear() {
    setTidIsSet(false);
    this.tid = 0;
    setPidIsSet(false);
    this.pid = 0;
    setCsidIsSet(false);
    this.csid = 0;
  }

  public long getTid() {
    return this.tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
    setTidIsSet(true);
  }

  public void unsetTid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TID_ISSET_ID);
  }

  /** Returns true if field tid is set (has been assigned a value) and false otherwise */
  public boolean isSetTid() {
    return EncodingUtils.testBit(__isset_bitfield, __TID_ISSET_ID);
  }

  public void setTidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TID_ISSET_ID, value);
  }

  public long getPid() {
    return this.pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
    setPidIsSet(true);
  }

  public void unsetPid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PID_ISSET_ID);
  }

  /** Returns true if field pid is set (has been assigned a value) and false otherwise */
  public boolean isSetPid() {
    return EncodingUtils.testBit(__isset_bitfield, __PID_ISSET_ID);
  }

  public void setPidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PID_ISSET_ID, value);
  }

  public long getCsid() {
    return this.csid;
  }

  public void setCsid(long csid) {
    this.csid = csid;
    setCsidIsSet(true);
  }

  public void unsetCsid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CSID_ISSET_ID);
  }

  /** Returns true if field csid is set (has been assigned a value) and false otherwise */
  public boolean isSetCsid() {
    return EncodingUtils.testBit(__isset_bitfield, __CSID_ISSET_ID);
  }

  public void setCsidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CSID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TID:
      if (value == null) {
        unsetTid();
      } else {
        setTid((Long)value);
      }
      break;

    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((Long)value);
      }
      break;

    case CSID:
      if (value == null) {
        unsetCsid();
      } else {
        setCsid((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TID:
      return Long.valueOf(getTid());

    case PID:
      return Long.valueOf(getPid());

    case CSID:
      return Long.valueOf(getCsid());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TID:
      return isSetTid();
    case PID:
      return isSetPid();
    case CSID:
      return isSetCsid();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetTeamSeasonsParam)
      return this.equals((GetTeamSeasonsParam)that);
    return false;
  }

  public boolean equals(GetTeamSeasonsParam that) {
    if (that == null)
      return false;

    boolean this_present_tid = true && this.isSetTid();
    boolean that_present_tid = true && that.isSetTid();
    if (this_present_tid || that_present_tid) {
      if (!(this_present_tid && that_present_tid))
        return false;
      if (this.tid != that.tid)
        return false;
    }

    boolean this_present_pid = true && this.isSetPid();
    boolean that_present_pid = true && that.isSetPid();
    if (this_present_pid || that_present_pid) {
      if (!(this_present_pid && that_present_pid))
        return false;
      if (this.pid != that.pid)
        return false;
    }

    boolean this_present_csid = true && this.isSetCsid();
    boolean that_present_csid = true && that.isSetCsid();
    if (this_present_csid || that_present_csid) {
      if (!(this_present_csid && that_present_csid))
        return false;
      if (this.csid != that.csid)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_tid = true && (isSetTid());
    list.add(present_tid);
    if (present_tid)
      list.add(tid);

    boolean present_pid = true && (isSetPid());
    list.add(present_pid);
    if (present_pid)
      list.add(pid);

    boolean present_csid = true && (isSetCsid());
    list.add(present_csid);
    if (present_csid)
      list.add(csid);

    return list.hashCode();
  }

  @Override
  public int compareTo(GetTeamSeasonsParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTid()).compareTo(other.isSetTid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tid, other.tid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPid()).compareTo(other.isSetPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pid, other.pid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCsid()).compareTo(other.isSetCsid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCsid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.csid, other.csid);
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
    StringBuilder sb = new StringBuilder("GetTeamSeasonsParam(");
    boolean first = true;

    if (isSetTid()) {
      sb.append("tid:");
      sb.append(this.tid);
      first = false;
    }
    if (isSetPid()) {
      if (!first) sb.append(", ");
      sb.append("pid:");
      sb.append(this.pid);
      first = false;
    }
    if (isSetCsid()) {
      if (!first) sb.append(", ");
      sb.append("csid:");
      sb.append(this.csid);
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

  private static class GetTeamSeasonsParamStandardSchemeFactory implements SchemeFactory {
    public GetTeamSeasonsParamStandardScheme getScheme() {
      return new GetTeamSeasonsParamStandardScheme();
    }
  }

  private static class GetTeamSeasonsParamStandardScheme extends StandardScheme<GetTeamSeasonsParam> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetTeamSeasonsParam struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.tid = iprot.readI64();
              struct.setTidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.pid = iprot.readI64();
              struct.setPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CSID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.csid = iprot.readI64();
              struct.setCsidIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetTeamSeasonsParam struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTid()) {
        oprot.writeFieldBegin(TID_FIELD_DESC);
        oprot.writeI64(struct.tid);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPid()) {
        oprot.writeFieldBegin(PID_FIELD_DESC);
        oprot.writeI64(struct.pid);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCsid()) {
        oprot.writeFieldBegin(CSID_FIELD_DESC);
        oprot.writeI64(struct.csid);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetTeamSeasonsParamTupleSchemeFactory implements SchemeFactory {
    public GetTeamSeasonsParamTupleScheme getScheme() {
      return new GetTeamSeasonsParamTupleScheme();
    }
  }

  private static class GetTeamSeasonsParamTupleScheme extends TupleScheme<GetTeamSeasonsParam> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetTeamSeasonsParam struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTid()) {
        optionals.set(0);
      }
      if (struct.isSetPid()) {
        optionals.set(1);
      }
      if (struct.isSetCsid()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTid()) {
        oprot.writeI64(struct.tid);
      }
      if (struct.isSetPid()) {
        oprot.writeI64(struct.pid);
      }
      if (struct.isSetCsid()) {
        oprot.writeI64(struct.csid);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetTeamSeasonsParam struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.tid = iprot.readI64();
        struct.setTidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.pid = iprot.readI64();
        struct.setPidIsSet(true);
      }
      if (incoming.get(2)) {
        struct.csid = iprot.readI64();
        struct.setCsidIsSet(true);
      }
    }
  }

}

