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
 * 获取赛季的榜单列表
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-12-9")
public class GetSeasonTopListsParam implements org.apache.thrift.TBase<GetSeasonTopListsParam, GetSeasonTopListsParam._Fields>, java.io.Serializable, Cloneable, Comparable<GetSeasonTopListsParam> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetSeasonTopListsParam");

  private static final org.apache.thrift.protocol.TField CID_FIELD_DESC = new org.apache.thrift.protocol.TField("cid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CSID_FIELD_DESC = new org.apache.thrift.protocol.TField("csid", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField GROUP_FIELD_DESC = new org.apache.thrift.protocol.TField("group", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField TYPES_FIELD_DESC = new org.apache.thrift.protocol.TField("types", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField SCOPE_FIELD_DESC = new org.apache.thrift.protocol.TField("scope", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField SCOPE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("scopeType", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField COMPETITOR_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("competitorType", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetSeasonTopListsParamStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetSeasonTopListsParamTupleSchemeFactory());
  }

  private long cid; // required
  private long csid; // optional
  private long type; // optional
  private long group; // optional
  private List<Long> types; // optional
  private long scope; // optional
  private com.lesports.qmt.sbd.api.common.ScopeType scopeType; // optional
  private com.lesports.qmt.sbd.api.common.CompetitorType competitorType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CID((short)1, "cid"),
    CSID((short)2, "csid"),
    TYPE((short)3, "type"),
    GROUP((short)4, "group"),
    TYPES((short)5, "types"),
    SCOPE((short)6, "scope"),
    /**
     * 
     * @see com.lesports.qmt.sbd.api.common.ScopeType
     */
    SCOPE_TYPE((short)7, "scopeType"),
    /**
     * 
     * @see com.lesports.qmt.sbd.api.common.CompetitorType
     */
    COMPETITOR_TYPE((short)8, "competitorType");

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
        case 1: // CID
          return CID;
        case 2: // CSID
          return CSID;
        case 3: // TYPE
          return TYPE;
        case 4: // GROUP
          return GROUP;
        case 5: // TYPES
          return TYPES;
        case 6: // SCOPE
          return SCOPE;
        case 7: // SCOPE_TYPE
          return SCOPE_TYPE;
        case 8: // COMPETITOR_TYPE
          return COMPETITOR_TYPE;
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
  private static final int __CID_ISSET_ID = 0;
  private static final int __CSID_ISSET_ID = 1;
  private static final int __TYPE_ISSET_ID = 2;
  private static final int __GROUP_ISSET_ID = 3;
  private static final int __SCOPE_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.CSID,_Fields.TYPE,_Fields.GROUP,_Fields.TYPES,_Fields.SCOPE,_Fields.SCOPE_TYPE,_Fields.COMPETITOR_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CID, new org.apache.thrift.meta_data.FieldMetaData("cid", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CSID, new org.apache.thrift.meta_data.FieldMetaData("csid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.GROUP, new org.apache.thrift.meta_data.FieldMetaData("group", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TYPES, new org.apache.thrift.meta_data.FieldMetaData("types", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.SCOPE, new org.apache.thrift.meta_data.FieldMetaData("scope", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SCOPE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("scopeType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.lesports.qmt.sbd.api.common.ScopeType.class)));
    tmpMap.put(_Fields.COMPETITOR_TYPE, new org.apache.thrift.meta_data.FieldMetaData("competitorType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.lesports.qmt.sbd.api.common.CompetitorType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetSeasonTopListsParam.class, metaDataMap);
  }

  public GetSeasonTopListsParam() {
  }

  public GetSeasonTopListsParam(
    long cid)
  {
    this();
    this.cid = cid;
    setCidIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetSeasonTopListsParam(GetSeasonTopListsParam other) {
    __isset_bitfield = other.__isset_bitfield;
    this.cid = other.cid;
    this.csid = other.csid;
    this.type = other.type;
    this.group = other.group;
    if (other.isSetTypes()) {
      List<Long> __this__types = new ArrayList<Long>(other.types);
      this.types = __this__types;
    }
    this.scope = other.scope;
    if (other.isSetScopeType()) {
      this.scopeType = other.scopeType;
    }
    if (other.isSetCompetitorType()) {
      this.competitorType = other.competitorType;
    }
  }

  public GetSeasonTopListsParam deepCopy() {
    return new GetSeasonTopListsParam(this);
  }

  @Override
  public void clear() {
    setCidIsSet(false);
    this.cid = 0;
    setCsidIsSet(false);
    this.csid = 0;
    setTypeIsSet(false);
    this.type = 0;
    setGroupIsSet(false);
    this.group = 0;
    this.types = null;
    setScopeIsSet(false);
    this.scope = 0;
    this.scopeType = null;
    this.competitorType = null;
  }

  public long getCid() {
    return this.cid;
  }

  public void setCid(long cid) {
    this.cid = cid;
    setCidIsSet(true);
  }

  public void unsetCid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CID_ISSET_ID);
  }

  /** Returns true if field cid is set (has been assigned a value) and false otherwise */
  public boolean isSetCid() {
    return EncodingUtils.testBit(__isset_bitfield, __CID_ISSET_ID);
  }

  public void setCidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CID_ISSET_ID, value);
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

  public long getType() {
    return this.type;
  }

  public void setType(long type) {
    this.type = type;
    setTypeIsSet(true);
  }

  public void unsetType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TYPE_ISSET_ID);
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return EncodingUtils.testBit(__isset_bitfield, __TYPE_ISSET_ID);
  }

  public void setTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TYPE_ISSET_ID, value);
  }

  public long getGroup() {
    return this.group;
  }

  public void setGroup(long group) {
    this.group = group;
    setGroupIsSet(true);
  }

  public void unsetGroup() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GROUP_ISSET_ID);
  }

  /** Returns true if field group is set (has been assigned a value) and false otherwise */
  public boolean isSetGroup() {
    return EncodingUtils.testBit(__isset_bitfield, __GROUP_ISSET_ID);
  }

  public void setGroupIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GROUP_ISSET_ID, value);
  }

  public int getTypesSize() {
    return (this.types == null) ? 0 : this.types.size();
  }

  public java.util.Iterator<Long> getTypesIterator() {
    return (this.types == null) ? null : this.types.iterator();
  }

  public void addToTypes(long elem) {
    if (this.types == null) {
      this.types = new ArrayList<Long>();
    }
    this.types.add(elem);
  }

  public List<Long> getTypes() {
    return this.types;
  }

  public void setTypes(List<Long> types) {
    this.types = types;
  }

  public void unsetTypes() {
    this.types = null;
  }

  /** Returns true if field types is set (has been assigned a value) and false otherwise */
  public boolean isSetTypes() {
    return this.types != null;
  }

  public void setTypesIsSet(boolean value) {
    if (!value) {
      this.types = null;
    }
  }

  public long getScope() {
    return this.scope;
  }

  public void setScope(long scope) {
    this.scope = scope;
    setScopeIsSet(true);
  }

  public void unsetScope() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SCOPE_ISSET_ID);
  }

  /** Returns true if field scope is set (has been assigned a value) and false otherwise */
  public boolean isSetScope() {
    return EncodingUtils.testBit(__isset_bitfield, __SCOPE_ISSET_ID);
  }

  public void setScopeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SCOPE_ISSET_ID, value);
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.ScopeType
   */
  public com.lesports.qmt.sbd.api.common.ScopeType getScopeType() {
    return this.scopeType;
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.ScopeType
   */
  public void setScopeType(com.lesports.qmt.sbd.api.common.ScopeType scopeType) {
    this.scopeType = scopeType;
  }

  public void unsetScopeType() {
    this.scopeType = null;
  }

  /** Returns true if field scopeType is set (has been assigned a value) and false otherwise */
  public boolean isSetScopeType() {
    return this.scopeType != null;
  }

  public void setScopeTypeIsSet(boolean value) {
    if (!value) {
      this.scopeType = null;
    }
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.CompetitorType
   */
  public com.lesports.qmt.sbd.api.common.CompetitorType getCompetitorType() {
    return this.competitorType;
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.CompetitorType
   */
  public void setCompetitorType(com.lesports.qmt.sbd.api.common.CompetitorType competitorType) {
    this.competitorType = competitorType;
  }

  public void unsetCompetitorType() {
    this.competitorType = null;
  }

  /** Returns true if field competitorType is set (has been assigned a value) and false otherwise */
  public boolean isSetCompetitorType() {
    return this.competitorType != null;
  }

  public void setCompetitorTypeIsSet(boolean value) {
    if (!value) {
      this.competitorType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CID:
      if (value == null) {
        unsetCid();
      } else {
        setCid((Long)value);
      }
      break;

    case CSID:
      if (value == null) {
        unsetCsid();
      } else {
        setCsid((Long)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((Long)value);
      }
      break;

    case GROUP:
      if (value == null) {
        unsetGroup();
      } else {
        setGroup((Long)value);
      }
      break;

    case TYPES:
      if (value == null) {
        unsetTypes();
      } else {
        setTypes((List<Long>)value);
      }
      break;

    case SCOPE:
      if (value == null) {
        unsetScope();
      } else {
        setScope((Long)value);
      }
      break;

    case SCOPE_TYPE:
      if (value == null) {
        unsetScopeType();
      } else {
        setScopeType((com.lesports.qmt.sbd.api.common.ScopeType)value);
      }
      break;

    case COMPETITOR_TYPE:
      if (value == null) {
        unsetCompetitorType();
      } else {
        setCompetitorType((com.lesports.qmt.sbd.api.common.CompetitorType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CID:
      return Long.valueOf(getCid());

    case CSID:
      return Long.valueOf(getCsid());

    case TYPE:
      return Long.valueOf(getType());

    case GROUP:
      return Long.valueOf(getGroup());

    case TYPES:
      return getTypes();

    case SCOPE:
      return Long.valueOf(getScope());

    case SCOPE_TYPE:
      return getScopeType();

    case COMPETITOR_TYPE:
      return getCompetitorType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CID:
      return isSetCid();
    case CSID:
      return isSetCsid();
    case TYPE:
      return isSetType();
    case GROUP:
      return isSetGroup();
    case TYPES:
      return isSetTypes();
    case SCOPE:
      return isSetScope();
    case SCOPE_TYPE:
      return isSetScopeType();
    case COMPETITOR_TYPE:
      return isSetCompetitorType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetSeasonTopListsParam)
      return this.equals((GetSeasonTopListsParam)that);
    return false;
  }

  public boolean equals(GetSeasonTopListsParam that) {
    if (that == null)
      return false;

    boolean this_present_cid = true;
    boolean that_present_cid = true;
    if (this_present_cid || that_present_cid) {
      if (!(this_present_cid && that_present_cid))
        return false;
      if (this.cid != that.cid)
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

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (this.type != that.type)
        return false;
    }

    boolean this_present_group = true && this.isSetGroup();
    boolean that_present_group = true && that.isSetGroup();
    if (this_present_group || that_present_group) {
      if (!(this_present_group && that_present_group))
        return false;
      if (this.group != that.group)
        return false;
    }

    boolean this_present_types = true && this.isSetTypes();
    boolean that_present_types = true && that.isSetTypes();
    if (this_present_types || that_present_types) {
      if (!(this_present_types && that_present_types))
        return false;
      if (!this.types.equals(that.types))
        return false;
    }

    boolean this_present_scope = true && this.isSetScope();
    boolean that_present_scope = true && that.isSetScope();
    if (this_present_scope || that_present_scope) {
      if (!(this_present_scope && that_present_scope))
        return false;
      if (this.scope != that.scope)
        return false;
    }

    boolean this_present_scopeType = true && this.isSetScopeType();
    boolean that_present_scopeType = true && that.isSetScopeType();
    if (this_present_scopeType || that_present_scopeType) {
      if (!(this_present_scopeType && that_present_scopeType))
        return false;
      if (!this.scopeType.equals(that.scopeType))
        return false;
    }

    boolean this_present_competitorType = true && this.isSetCompetitorType();
    boolean that_present_competitorType = true && that.isSetCompetitorType();
    if (this_present_competitorType || that_present_competitorType) {
      if (!(this_present_competitorType && that_present_competitorType))
        return false;
      if (!this.competitorType.equals(that.competitorType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_cid = true;
    list.add(present_cid);
    if (present_cid)
      list.add(cid);

    boolean present_csid = true && (isSetCsid());
    list.add(present_csid);
    if (present_csid)
      list.add(csid);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type);

    boolean present_group = true && (isSetGroup());
    list.add(present_group);
    if (present_group)
      list.add(group);

    boolean present_types = true && (isSetTypes());
    list.add(present_types);
    if (present_types)
      list.add(types);

    boolean present_scope = true && (isSetScope());
    list.add(present_scope);
    if (present_scope)
      list.add(scope);

    boolean present_scopeType = true && (isSetScopeType());
    list.add(present_scopeType);
    if (present_scopeType)
      list.add(scopeType.getValue());

    boolean present_competitorType = true && (isSetCompetitorType());
    list.add(present_competitorType);
    if (present_competitorType)
      list.add(competitorType.getValue());

    return list.hashCode();
  }

  @Override
  public int compareTo(GetSeasonTopListsParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCid()).compareTo(other.isSetCid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cid, other.cid);
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
    lastComparison = Boolean.valueOf(isSetGroup()).compareTo(other.isSetGroup());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroup()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.group, other.group);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTypes()).compareTo(other.isSetTypes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTypes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.types, other.types);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetScope()).compareTo(other.isSetScope());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScope()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.scope, other.scope);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetScopeType()).compareTo(other.isSetScopeType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScopeType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.scopeType, other.scopeType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCompetitorType()).compareTo(other.isSetCompetitorType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompetitorType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.competitorType, other.competitorType);
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
    StringBuilder sb = new StringBuilder("GetSeasonTopListsParam(");
    boolean first = true;

    sb.append("cid:");
    sb.append(this.cid);
    first = false;
    if (isSetCsid()) {
      if (!first) sb.append(", ");
      sb.append("csid:");
      sb.append(this.csid);
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      sb.append(this.type);
      first = false;
    }
    if (isSetGroup()) {
      if (!first) sb.append(", ");
      sb.append("group:");
      sb.append(this.group);
      first = false;
    }
    if (isSetTypes()) {
      if (!first) sb.append(", ");
      sb.append("types:");
      if (this.types == null) {
        sb.append("null");
      } else {
        sb.append(this.types);
      }
      first = false;
    }
    if (isSetScope()) {
      if (!first) sb.append(", ");
      sb.append("scope:");
      sb.append(this.scope);
      first = false;
    }
    if (isSetScopeType()) {
      if (!first) sb.append(", ");
      sb.append("scopeType:");
      if (this.scopeType == null) {
        sb.append("null");
      } else {
        sb.append(this.scopeType);
      }
      first = false;
    }
    if (isSetCompetitorType()) {
      if (!first) sb.append(", ");
      sb.append("competitorType:");
      if (this.competitorType == null) {
        sb.append("null");
      } else {
        sb.append(this.competitorType);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetCid()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'cid' is unset! Struct:" + toString());
    }

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

  private static class GetSeasonTopListsParamStandardSchemeFactory implements SchemeFactory {
    public GetSeasonTopListsParamStandardScheme getScheme() {
      return new GetSeasonTopListsParamStandardScheme();
    }
  }

  private static class GetSeasonTopListsParamStandardScheme extends StandardScheme<GetSeasonTopListsParam> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetSeasonTopListsParam struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.cid = iprot.readI64();
              struct.setCidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CSID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.csid = iprot.readI64();
              struct.setCsidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.type = iprot.readI64();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // GROUP
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.group = iprot.readI64();
              struct.setGroupIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TYPES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.types = new ArrayList<Long>(_list8.size);
                long _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = iprot.readI64();
                  struct.types.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setTypesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SCOPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.scope = iprot.readI64();
              struct.setScopeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // SCOPE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.scopeType = com.lesports.qmt.sbd.api.common.ScopeType.findByValue(iprot.readI32());
              struct.setScopeTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // COMPETITOR_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.competitorType = com.lesports.qmt.sbd.api.common.CompetitorType.findByValue(iprot.readI32());
              struct.setCompetitorTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetSeasonTopListsParam struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CID_FIELD_DESC);
      oprot.writeI64(struct.cid);
      oprot.writeFieldEnd();
      if (struct.isSetCsid()) {
        oprot.writeFieldBegin(CSID_FIELD_DESC);
        oprot.writeI64(struct.csid);
        oprot.writeFieldEnd();
      }
      if (struct.isSetType()) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI64(struct.type);
        oprot.writeFieldEnd();
      }
      if (struct.isSetGroup()) {
        oprot.writeFieldBegin(GROUP_FIELD_DESC);
        oprot.writeI64(struct.group);
        oprot.writeFieldEnd();
      }
      if (struct.types != null) {
        if (struct.isSetTypes()) {
          oprot.writeFieldBegin(TYPES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.types.size()));
            for (long _iter11 : struct.types)
            {
              oprot.writeI64(_iter11);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetScope()) {
        oprot.writeFieldBegin(SCOPE_FIELD_DESC);
        oprot.writeI64(struct.scope);
        oprot.writeFieldEnd();
      }
      if (struct.scopeType != null) {
        if (struct.isSetScopeType()) {
          oprot.writeFieldBegin(SCOPE_TYPE_FIELD_DESC);
          oprot.writeI32(struct.scopeType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.competitorType != null) {
        if (struct.isSetCompetitorType()) {
          oprot.writeFieldBegin(COMPETITOR_TYPE_FIELD_DESC);
          oprot.writeI32(struct.competitorType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetSeasonTopListsParamTupleSchemeFactory implements SchemeFactory {
    public GetSeasonTopListsParamTupleScheme getScheme() {
      return new GetSeasonTopListsParamTupleScheme();
    }
  }

  private static class GetSeasonTopListsParamTupleScheme extends TupleScheme<GetSeasonTopListsParam> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetSeasonTopListsParam struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.cid);
      BitSet optionals = new BitSet();
      if (struct.isSetCsid()) {
        optionals.set(0);
      }
      if (struct.isSetType()) {
        optionals.set(1);
      }
      if (struct.isSetGroup()) {
        optionals.set(2);
      }
      if (struct.isSetTypes()) {
        optionals.set(3);
      }
      if (struct.isSetScope()) {
        optionals.set(4);
      }
      if (struct.isSetScopeType()) {
        optionals.set(5);
      }
      if (struct.isSetCompetitorType()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetCsid()) {
        oprot.writeI64(struct.csid);
      }
      if (struct.isSetType()) {
        oprot.writeI64(struct.type);
      }
      if (struct.isSetGroup()) {
        oprot.writeI64(struct.group);
      }
      if (struct.isSetTypes()) {
        {
          oprot.writeI32(struct.types.size());
          for (long _iter12 : struct.types)
          {
            oprot.writeI64(_iter12);
          }
        }
      }
      if (struct.isSetScope()) {
        oprot.writeI64(struct.scope);
      }
      if (struct.isSetScopeType()) {
        oprot.writeI32(struct.scopeType.getValue());
      }
      if (struct.isSetCompetitorType()) {
        oprot.writeI32(struct.competitorType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetSeasonTopListsParam struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.cid = iprot.readI64();
      struct.setCidIsSet(true);
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.csid = iprot.readI64();
        struct.setCsidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.type = iprot.readI64();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.group = iprot.readI64();
        struct.setGroupIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.types = new ArrayList<Long>(_list13.size);
          long _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = iprot.readI64();
            struct.types.add(_elem14);
          }
        }
        struct.setTypesIsSet(true);
      }
      if (incoming.get(4)) {
        struct.scope = iprot.readI64();
        struct.setScopeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.scopeType = com.lesports.qmt.sbd.api.common.ScopeType.findByValue(iprot.readI32());
        struct.setScopeTypeIsSet(true);
      }
      if (incoming.get(6)) {
        struct.competitorType = com.lesports.qmt.sbd.api.common.CompetitorType.findByValue(iprot.readI32());
        struct.setCompetitorTypeIsSet(true);
      }
    }
  }

}

