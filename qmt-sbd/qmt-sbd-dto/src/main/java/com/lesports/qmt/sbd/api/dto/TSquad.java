/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbd.api.dto;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-12-21")
public class TSquad implements org.apache.thrift.TBase<TSquad, TSquad._Fields>, java.io.Serializable, Cloneable, Comparable<TSquad> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSquad");

  private static final org.apache.thrift.protocol.TField TID_FIELD_DESC = new org.apache.thrift.protocol.TField("tid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PLAYERS_FIELD_DESC = new org.apache.thrift.protocol.TField("players", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField FORMATION_FIELD_DESC = new org.apache.thrift.protocol.TField("formation", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField MULTI_LANG_NAMES_FIELD_DESC = new org.apache.thrift.protocol.TField("multiLangNames", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField COACHES_FIELD_DESC = new org.apache.thrift.protocol.TField("coaches", org.apache.thrift.protocol.TType.LIST, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TSquadStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TSquadTupleSchemeFactory());
  }

  private long tid; // required
  private String name; // required
  private List<com.lesports.qmt.sbd.api.dto.TSimplePlayer> players; // optional
  private String formation; // optional
  private List<com.lesports.api.common.LangString> multiLangNames; // optional
  private List<com.lesports.qmt.sbd.api.dto.TSimpleCoach> coaches; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TID((short)1, "tid"),
    NAME((short)2, "name"),
    PLAYERS((short)3, "players"),
    FORMATION((short)4, "formation"),
    MULTI_LANG_NAMES((short)5, "multiLangNames"),
    COACHES((short)6, "coaches");

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
        case 2: // NAME
          return NAME;
        case 3: // PLAYERS
          return PLAYERS;
        case 4: // FORMATION
          return FORMATION;
        case 5: // MULTI_LANG_NAMES
          return MULTI_LANG_NAMES;
        case 6: // COACHES
          return COACHES;
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
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PLAYERS,_Fields.FORMATION,_Fields.MULTI_LANG_NAMES,_Fields.COACHES};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TID, new org.apache.thrift.meta_data.FieldMetaData("tid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PLAYERS, new org.apache.thrift.meta_data.FieldMetaData("players", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.lesports.qmt.sbd.api.dto.TSimplePlayer.class))));
    tmpMap.put(_Fields.FORMATION, new org.apache.thrift.meta_data.FieldMetaData("formation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MULTI_LANG_NAMES, new org.apache.thrift.meta_data.FieldMetaData("multiLangNames", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.lesports.api.common.LangString.class))));
    tmpMap.put(_Fields.COACHES, new org.apache.thrift.meta_data.FieldMetaData("coaches", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.lesports.qmt.sbd.api.dto.TSimpleCoach.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSquad.class, metaDataMap);
  }

  public TSquad() {
  }

  public TSquad(
    long tid,
    String name)
  {
    this();
    this.tid = tid;
    setTidIsSet(true);
    this.name = name;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSquad(TSquad other) {
    __isset_bitfield = other.__isset_bitfield;
    this.tid = other.tid;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetPlayers()) {
      List<com.lesports.qmt.sbd.api.dto.TSimplePlayer> __this__players = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimplePlayer>(other.players.size());
      for (com.lesports.qmt.sbd.api.dto.TSimplePlayer other_element : other.players) {
        __this__players.add(new com.lesports.qmt.sbd.api.dto.TSimplePlayer(other_element));
      }
      this.players = __this__players;
    }
    if (other.isSetFormation()) {
      this.formation = other.formation;
    }
    if (other.isSetMultiLangNames()) {
      List<com.lesports.api.common.LangString> __this__multiLangNames = new ArrayList<com.lesports.api.common.LangString>(other.multiLangNames.size());
      for (com.lesports.api.common.LangString other_element : other.multiLangNames) {
        __this__multiLangNames.add(new com.lesports.api.common.LangString(other_element));
      }
      this.multiLangNames = __this__multiLangNames;
    }
    if (other.isSetCoaches()) {
      List<com.lesports.qmt.sbd.api.dto.TSimpleCoach> __this__coaches = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimpleCoach>(other.coaches.size());
      for (com.lesports.qmt.sbd.api.dto.TSimpleCoach other_element : other.coaches) {
        __this__coaches.add(new com.lesports.qmt.sbd.api.dto.TSimpleCoach(other_element));
      }
      this.coaches = __this__coaches;
    }
  }

  public TSquad deepCopy() {
    return new TSquad(this);
  }

  @Override
  public void clear() {
    setTidIsSet(false);
    this.tid = 0;
    this.name = null;
    this.players = null;
    this.formation = null;
    this.multiLangNames = null;
    this.coaches = null;
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

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public int getPlayersSize() {
    return (this.players == null) ? 0 : this.players.size();
  }

  public java.util.Iterator<com.lesports.qmt.sbd.api.dto.TSimplePlayer> getPlayersIterator() {
    return (this.players == null) ? null : this.players.iterator();
  }

  public void addToPlayers(com.lesports.qmt.sbd.api.dto.TSimplePlayer elem) {
    if (this.players == null) {
      this.players = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimplePlayer>();
    }
    this.players.add(elem);
  }

  public List<com.lesports.qmt.sbd.api.dto.TSimplePlayer> getPlayers() {
    return this.players;
  }

  public void setPlayers(List<com.lesports.qmt.sbd.api.dto.TSimplePlayer> players) {
    this.players = players;
  }

  public void unsetPlayers() {
    this.players = null;
  }

  /** Returns true if field players is set (has been assigned a value) and false otherwise */
  public boolean isSetPlayers() {
    return this.players != null;
  }

  public void setPlayersIsSet(boolean value) {
    if (!value) {
      this.players = null;
    }
  }

  public String getFormation() {
    return this.formation;
  }

  public void setFormation(String formation) {
    this.formation = formation;
  }

  public void unsetFormation() {
    this.formation = null;
  }

  /** Returns true if field formation is set (has been assigned a value) and false otherwise */
  public boolean isSetFormation() {
    return this.formation != null;
  }

  public void setFormationIsSet(boolean value) {
    if (!value) {
      this.formation = null;
    }
  }

  public int getMultiLangNamesSize() {
    return (this.multiLangNames == null) ? 0 : this.multiLangNames.size();
  }

  public java.util.Iterator<com.lesports.api.common.LangString> getMultiLangNamesIterator() {
    return (this.multiLangNames == null) ? null : this.multiLangNames.iterator();
  }

  public void addToMultiLangNames(com.lesports.api.common.LangString elem) {
    if (this.multiLangNames == null) {
      this.multiLangNames = new ArrayList<com.lesports.api.common.LangString>();
    }
    this.multiLangNames.add(elem);
  }

  public List<com.lesports.api.common.LangString> getMultiLangNames() {
    return this.multiLangNames;
  }

  public void setMultiLangNames(List<com.lesports.api.common.LangString> multiLangNames) {
    this.multiLangNames = multiLangNames;
  }

  public void unsetMultiLangNames() {
    this.multiLangNames = null;
  }

  /** Returns true if field multiLangNames is set (has been assigned a value) and false otherwise */
  public boolean isSetMultiLangNames() {
    return this.multiLangNames != null;
  }

  public void setMultiLangNamesIsSet(boolean value) {
    if (!value) {
      this.multiLangNames = null;
    }
  }

  public int getCoachesSize() {
    return (this.coaches == null) ? 0 : this.coaches.size();
  }

  public java.util.Iterator<com.lesports.qmt.sbd.api.dto.TSimpleCoach> getCoachesIterator() {
    return (this.coaches == null) ? null : this.coaches.iterator();
  }

  public void addToCoaches(com.lesports.qmt.sbd.api.dto.TSimpleCoach elem) {
    if (this.coaches == null) {
      this.coaches = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimpleCoach>();
    }
    this.coaches.add(elem);
  }

  public List<com.lesports.qmt.sbd.api.dto.TSimpleCoach> getCoaches() {
    return this.coaches;
  }

  public void setCoaches(List<com.lesports.qmt.sbd.api.dto.TSimpleCoach> coaches) {
    this.coaches = coaches;
  }

  public void unsetCoaches() {
    this.coaches = null;
  }

  /** Returns true if field coaches is set (has been assigned a value) and false otherwise */
  public boolean isSetCoaches() {
    return this.coaches != null;
  }

  public void setCoachesIsSet(boolean value) {
    if (!value) {
      this.coaches = null;
    }
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

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case PLAYERS:
      if (value == null) {
        unsetPlayers();
      } else {
        setPlayers((List<com.lesports.qmt.sbd.api.dto.TSimplePlayer>)value);
      }
      break;

    case FORMATION:
      if (value == null) {
        unsetFormation();
      } else {
        setFormation((String)value);
      }
      break;

    case MULTI_LANG_NAMES:
      if (value == null) {
        unsetMultiLangNames();
      } else {
        setMultiLangNames((List<com.lesports.api.common.LangString>)value);
      }
      break;

    case COACHES:
      if (value == null) {
        unsetCoaches();
      } else {
        setCoaches((List<com.lesports.qmt.sbd.api.dto.TSimpleCoach>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TID:
      return Long.valueOf(getTid());

    case NAME:
      return getName();

    case PLAYERS:
      return getPlayers();

    case FORMATION:
      return getFormation();

    case MULTI_LANG_NAMES:
      return getMultiLangNames();

    case COACHES:
      return getCoaches();

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
    case NAME:
      return isSetName();
    case PLAYERS:
      return isSetPlayers();
    case FORMATION:
      return isSetFormation();
    case MULTI_LANG_NAMES:
      return isSetMultiLangNames();
    case COACHES:
      return isSetCoaches();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TSquad)
      return this.equals((TSquad)that);
    return false;
  }

  public boolean equals(TSquad that) {
    if (that == null)
      return false;

    boolean this_present_tid = true;
    boolean that_present_tid = true;
    if (this_present_tid || that_present_tid) {
      if (!(this_present_tid && that_present_tid))
        return false;
      if (this.tid != that.tid)
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_players = true && this.isSetPlayers();
    boolean that_present_players = true && that.isSetPlayers();
    if (this_present_players || that_present_players) {
      if (!(this_present_players && that_present_players))
        return false;
      if (!this.players.equals(that.players))
        return false;
    }

    boolean this_present_formation = true && this.isSetFormation();
    boolean that_present_formation = true && that.isSetFormation();
    if (this_present_formation || that_present_formation) {
      if (!(this_present_formation && that_present_formation))
        return false;
      if (!this.formation.equals(that.formation))
        return false;
    }

    boolean this_present_multiLangNames = true && this.isSetMultiLangNames();
    boolean that_present_multiLangNames = true && that.isSetMultiLangNames();
    if (this_present_multiLangNames || that_present_multiLangNames) {
      if (!(this_present_multiLangNames && that_present_multiLangNames))
        return false;
      if (!this.multiLangNames.equals(that.multiLangNames))
        return false;
    }

    boolean this_present_coaches = true && this.isSetCoaches();
    boolean that_present_coaches = true && that.isSetCoaches();
    if (this_present_coaches || that_present_coaches) {
      if (!(this_present_coaches && that_present_coaches))
        return false;
      if (!this.coaches.equals(that.coaches))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_tid = true;
    list.add(present_tid);
    if (present_tid)
      list.add(tid);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_players = true && (isSetPlayers());
    list.add(present_players);
    if (present_players)
      list.add(players);

    boolean present_formation = true && (isSetFormation());
    list.add(present_formation);
    if (present_formation)
      list.add(formation);

    boolean present_multiLangNames = true && (isSetMultiLangNames());
    list.add(present_multiLangNames);
    if (present_multiLangNames)
      list.add(multiLangNames);

    boolean present_coaches = true && (isSetCoaches());
    list.add(present_coaches);
    if (present_coaches)
      list.add(coaches);

    return list.hashCode();
  }

  @Override
  public int compareTo(TSquad other) {
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
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlayers()).compareTo(other.isSetPlayers());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlayers()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.players, other.players);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFormation()).compareTo(other.isSetFormation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFormation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.formation, other.formation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMultiLangNames()).compareTo(other.isSetMultiLangNames());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMultiLangNames()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.multiLangNames, other.multiLangNames);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCoaches()).compareTo(other.isSetCoaches());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCoaches()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.coaches, other.coaches);
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
    StringBuilder sb = new StringBuilder("TSquad(");
    boolean first = true;

    sb.append("tid:");
    sb.append(this.tid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (isSetPlayers()) {
      if (!first) sb.append(", ");
      sb.append("players:");
      if (this.players == null) {
        sb.append("null");
      } else {
        sb.append(this.players);
      }
      first = false;
    }
    if (isSetFormation()) {
      if (!first) sb.append(", ");
      sb.append("formation:");
      if (this.formation == null) {
        sb.append("null");
      } else {
        sb.append(this.formation);
      }
      first = false;
    }
    if (isSetMultiLangNames()) {
      if (!first) sb.append(", ");
      sb.append("multiLangNames:");
      if (this.multiLangNames == null) {
        sb.append("null");
      } else {
        sb.append(this.multiLangNames);
      }
      first = false;
    }
    if (isSetCoaches()) {
      if (!first) sb.append(", ");
      sb.append("coaches:");
      if (this.coaches == null) {
        sb.append("null");
      } else {
        sb.append(this.coaches);
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

  private static class TSquadStandardSchemeFactory implements SchemeFactory {
    public TSquadStandardScheme getScheme() {
      return new TSquadStandardScheme();
    }
  }

  private static class TSquadStandardScheme extends StandardScheme<TSquad> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSquad struct) throws org.apache.thrift.TException {
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
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PLAYERS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list126 = iprot.readListBegin();
                struct.players = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimplePlayer>(_list126.size);
                com.lesports.qmt.sbd.api.dto.TSimplePlayer _elem127;
                for (int _i128 = 0; _i128 < _list126.size; ++_i128)
                {
                  _elem127 = new com.lesports.qmt.sbd.api.dto.TSimplePlayer();
                  _elem127.read(iprot);
                  struct.players.add(_elem127);
                }
                iprot.readListEnd();
              }
              struct.setPlayersIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FORMATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.formation = iprot.readString();
              struct.setFormationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MULTI_LANG_NAMES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list129 = iprot.readListBegin();
                struct.multiLangNames = new ArrayList<com.lesports.api.common.LangString>(_list129.size);
                com.lesports.api.common.LangString _elem130;
                for (int _i131 = 0; _i131 < _list129.size; ++_i131)
                {
                  _elem130 = new com.lesports.api.common.LangString();
                  _elem130.read(iprot);
                  struct.multiLangNames.add(_elem130);
                }
                iprot.readListEnd();
              }
              struct.setMultiLangNamesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // COACHES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list132 = iprot.readListBegin();
                struct.coaches = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimpleCoach>(_list132.size);
                com.lesports.qmt.sbd.api.dto.TSimpleCoach _elem133;
                for (int _i134 = 0; _i134 < _list132.size; ++_i134)
                {
                  _elem133 = new com.lesports.qmt.sbd.api.dto.TSimpleCoach();
                  _elem133.read(iprot);
                  struct.coaches.add(_elem133);
                }
                iprot.readListEnd();
              }
              struct.setCoachesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSquad struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TID_FIELD_DESC);
      oprot.writeI64(struct.tid);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.players != null) {
        if (struct.isSetPlayers()) {
          oprot.writeFieldBegin(PLAYERS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.players.size()));
            for (com.lesports.qmt.sbd.api.dto.TSimplePlayer _iter135 : struct.players)
            {
              _iter135.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.formation != null) {
        if (struct.isSetFormation()) {
          oprot.writeFieldBegin(FORMATION_FIELD_DESC);
          oprot.writeString(struct.formation);
          oprot.writeFieldEnd();
        }
      }
      if (struct.multiLangNames != null) {
        if (struct.isSetMultiLangNames()) {
          oprot.writeFieldBegin(MULTI_LANG_NAMES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.multiLangNames.size()));
            for (com.lesports.api.common.LangString _iter136 : struct.multiLangNames)
            {
              _iter136.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.coaches != null) {
        if (struct.isSetCoaches()) {
          oprot.writeFieldBegin(COACHES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.coaches.size()));
            for (com.lesports.qmt.sbd.api.dto.TSimpleCoach _iter137 : struct.coaches)
            {
              _iter137.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TSquadTupleSchemeFactory implements SchemeFactory {
    public TSquadTupleScheme getScheme() {
      return new TSquadTupleScheme();
    }
  }

  private static class TSquadTupleScheme extends TupleScheme<TSquad> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSquad struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTid()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetPlayers()) {
        optionals.set(2);
      }
      if (struct.isSetFormation()) {
        optionals.set(3);
      }
      if (struct.isSetMultiLangNames()) {
        optionals.set(4);
      }
      if (struct.isSetCoaches()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetTid()) {
        oprot.writeI64(struct.tid);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetPlayers()) {
        {
          oprot.writeI32(struct.players.size());
          for (com.lesports.qmt.sbd.api.dto.TSimplePlayer _iter138 : struct.players)
          {
            _iter138.write(oprot);
          }
        }
      }
      if (struct.isSetFormation()) {
        oprot.writeString(struct.formation);
      }
      if (struct.isSetMultiLangNames()) {
        {
          oprot.writeI32(struct.multiLangNames.size());
          for (com.lesports.api.common.LangString _iter139 : struct.multiLangNames)
          {
            _iter139.write(oprot);
          }
        }
      }
      if (struct.isSetCoaches()) {
        {
          oprot.writeI32(struct.coaches.size());
          for (com.lesports.qmt.sbd.api.dto.TSimpleCoach _iter140 : struct.coaches)
          {
            _iter140.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSquad struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.tid = iprot.readI64();
        struct.setTidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list141 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.players = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimplePlayer>(_list141.size);
          com.lesports.qmt.sbd.api.dto.TSimplePlayer _elem142;
          for (int _i143 = 0; _i143 < _list141.size; ++_i143)
          {
            _elem142 = new com.lesports.qmt.sbd.api.dto.TSimplePlayer();
            _elem142.read(iprot);
            struct.players.add(_elem142);
          }
        }
        struct.setPlayersIsSet(true);
      }
      if (incoming.get(3)) {
        struct.formation = iprot.readString();
        struct.setFormationIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list144 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.multiLangNames = new ArrayList<com.lesports.api.common.LangString>(_list144.size);
          com.lesports.api.common.LangString _elem145;
          for (int _i146 = 0; _i146 < _list144.size; ++_i146)
          {
            _elem145 = new com.lesports.api.common.LangString();
            _elem145.read(iprot);
            struct.multiLangNames.add(_elem145);
          }
        }
        struct.setMultiLangNamesIsSet(true);
      }
      if (incoming.get(5)) {
        {
          org.apache.thrift.protocol.TList _list147 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.coaches = new ArrayList<com.lesports.qmt.sbd.api.dto.TSimpleCoach>(_list147.size);
          com.lesports.qmt.sbd.api.dto.TSimpleCoach _elem148;
          for (int _i149 = 0; _i149 < _list147.size; ++_i149)
          {
            _elem148 = new com.lesports.qmt.sbd.api.dto.TSimpleCoach();
            _elem148.read(iprot);
            struct.coaches.add(_elem148);
          }
        }
        struct.setCoachesIsSet(true);
      }
    }
  }

}

