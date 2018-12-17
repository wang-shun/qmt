/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbc.api.dto;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * 专辑信息
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-10-28")
public class TCarousel implements org.apache.thrift.TBase<TCarousel, TCarousel._Fields>, java.io.Serializable, Cloneable, Comparable<TCarousel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TCarousel");

  private static final org.apache.thrift.protocol.TField CHANNEL_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("channelId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField IMAGE_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("imageUrl", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SORT_FIELD_DESC = new org.apache.thrift.protocol.TField("sort", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField SHOW_CORNER_MARK_FIELD_DESC = new org.apache.thrift.protocol.TField("showCornerMark", org.apache.thrift.protocol.TType.BOOL, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TCarouselStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TCarouselTupleSchemeFactory());
  }

  private long channelId; // required
  private String imageUrl; // optional
  private int sort; // optional
  private long id; // optional
  private boolean showCornerMark; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CHANNEL_ID((short)1, "channelId"),
    IMAGE_URL((short)2, "imageUrl"),
    SORT((short)3, "sort"),
    ID((short)4, "id"),
    SHOW_CORNER_MARK((short)5, "showCornerMark");

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
        case 1: // CHANNEL_ID
          return CHANNEL_ID;
        case 2: // IMAGE_URL
          return IMAGE_URL;
        case 3: // SORT
          return SORT;
        case 4: // ID
          return ID;
        case 5: // SHOW_CORNER_MARK
          return SHOW_CORNER_MARK;
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
  private static final int __CHANNELID_ISSET_ID = 0;
  private static final int __SORT_ISSET_ID = 1;
  private static final int __ID_ISSET_ID = 2;
  private static final int __SHOWCORNERMARK_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.IMAGE_URL, _Fields.SORT, _Fields.ID, _Fields.SHOW_CORNER_MARK};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CHANNEL_ID, new org.apache.thrift.meta_data.FieldMetaData("channelId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.IMAGE_URL, new org.apache.thrift.meta_data.FieldMetaData("imageUrl", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SORT, new org.apache.thrift.meta_data.FieldMetaData("sort", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SHOW_CORNER_MARK, new org.apache.thrift.meta_data.FieldMetaData("showCornerMark", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TCarousel.class, metaDataMap);
  }

  public TCarousel() {
  }

  public TCarousel(
    long channelId)
  {
    this();
    this.channelId = channelId;
    setChannelIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TCarousel(TCarousel other) {
    __isset_bitfield = other.__isset_bitfield;
    this.channelId = other.channelId;
    if (other.isSetImageUrl()) {
      this.imageUrl = other.imageUrl;
    }
    this.sort = other.sort;
    this.id = other.id;
    this.showCornerMark = other.showCornerMark;
  }

  public TCarousel deepCopy() {
    return new TCarousel(this);
  }

  @Override
  public void clear() {
    setChannelIdIsSet(false);
    this.channelId = 0;
    this.imageUrl = null;
    setSortIsSet(false);
    this.sort = 0;
    setIdIsSet(false);
    this.id = 0;
    setShowCornerMarkIsSet(false);
    this.showCornerMark = false;
  }

  public long getChannelId() {
    return this.channelId;
  }

  public void setChannelId(long channelId) {
    this.channelId = channelId;
    setChannelIdIsSet(true);
  }

  public void unsetChannelId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CHANNELID_ISSET_ID);
  }

  /** Returns true if field channelId is set (has been assigned a value) and false otherwise */
  public boolean isSetChannelId() {
    return EncodingUtils.testBit(__isset_bitfield, __CHANNELID_ISSET_ID);
  }

  public void setChannelIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CHANNELID_ISSET_ID, value);
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void unsetImageUrl() {
    this.imageUrl = null;
  }

  /** Returns true if field imageUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetImageUrl() {
    return this.imageUrl != null;
  }

  public void setImageUrlIsSet(boolean value) {
    if (!value) {
      this.imageUrl = null;
    }
  }

  public int getSort() {
    return this.sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
    setSortIsSet(true);
  }

  public void unsetSort() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SORT_ISSET_ID);
  }

  /** Returns true if field sort is set (has been assigned a value) and false otherwise */
  public boolean isSetSort() {
    return EncodingUtils.testBit(__isset_bitfield, __SORT_ISSET_ID);
  }

  public void setSortIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SORT_ISSET_ID, value);
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
    setIdIsSet(true);
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public boolean isShowCornerMark() {
    return this.showCornerMark;
  }

  public void setShowCornerMark(boolean showCornerMark) {
    this.showCornerMark = showCornerMark;
    setShowCornerMarkIsSet(true);
  }

  public void unsetShowCornerMark() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHOWCORNERMARK_ISSET_ID);
  }

  /** Returns true if field showCornerMark is set (has been assigned a value) and false otherwise */
  public boolean isSetShowCornerMark() {
    return EncodingUtils.testBit(__isset_bitfield, __SHOWCORNERMARK_ISSET_ID);
  }

  public void setShowCornerMarkIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHOWCORNERMARK_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CHANNEL_ID:
      if (value == null) {
        unsetChannelId();
      } else {
        setChannelId((Long)value);
      }
      break;

    case IMAGE_URL:
      if (value == null) {
        unsetImageUrl();
      } else {
        setImageUrl((String)value);
      }
      break;

    case SORT:
      if (value == null) {
        unsetSort();
      } else {
        setSort((Integer)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

    case SHOW_CORNER_MARK:
      if (value == null) {
        unsetShowCornerMark();
      } else {
        setShowCornerMark((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CHANNEL_ID:
      return Long.valueOf(getChannelId());

    case IMAGE_URL:
      return getImageUrl();

    case SORT:
      return Integer.valueOf(getSort());

    case ID:
      return Long.valueOf(getId());

    case SHOW_CORNER_MARK:
      return Boolean.valueOf(isShowCornerMark());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CHANNEL_ID:
      return isSetChannelId();
    case IMAGE_URL:
      return isSetImageUrl();
    case SORT:
      return isSetSort();
    case ID:
      return isSetId();
    case SHOW_CORNER_MARK:
      return isSetShowCornerMark();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TCarousel)
      return this.equals((TCarousel)that);
    return false;
  }

  public boolean equals(TCarousel that) {
    if (that == null)
      return false;

    boolean this_present_channelId = true;
    boolean that_present_channelId = true;
    if (this_present_channelId || that_present_channelId) {
      if (!(this_present_channelId && that_present_channelId))
        return false;
      if (this.channelId != that.channelId)
        return false;
    }

    boolean this_present_imageUrl = true && this.isSetImageUrl();
    boolean that_present_imageUrl = true && that.isSetImageUrl();
    if (this_present_imageUrl || that_present_imageUrl) {
      if (!(this_present_imageUrl && that_present_imageUrl))
        return false;
      if (!this.imageUrl.equals(that.imageUrl))
        return false;
    }

    boolean this_present_sort = true && this.isSetSort();
    boolean that_present_sort = true && that.isSetSort();
    if (this_present_sort || that_present_sort) {
      if (!(this_present_sort && that_present_sort))
        return false;
      if (this.sort != that.sort)
        return false;
    }

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_showCornerMark = true && this.isSetShowCornerMark();
    boolean that_present_showCornerMark = true && that.isSetShowCornerMark();
    if (this_present_showCornerMark || that_present_showCornerMark) {
      if (!(this_present_showCornerMark && that_present_showCornerMark))
        return false;
      if (this.showCornerMark != that.showCornerMark)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_channelId = true;
    list.add(present_channelId);
    if (present_channelId)
      list.add(channelId);

    boolean present_imageUrl = true && (isSetImageUrl());
    list.add(present_imageUrl);
    if (present_imageUrl)
      list.add(imageUrl);

    boolean present_sort = true && (isSetSort());
    list.add(present_sort);
    if (present_sort)
      list.add(sort);

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_showCornerMark = true && (isSetShowCornerMark());
    list.add(present_showCornerMark);
    if (present_showCornerMark)
      list.add(showCornerMark);

    return list.hashCode();
  }

  @Override
  public int compareTo(TCarousel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetChannelId()).compareTo(other.isSetChannelId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChannelId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.channelId, other.channelId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetImageUrl()).compareTo(other.isSetImageUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetImageUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.imageUrl, other.imageUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSort()).compareTo(other.isSetSort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sort, other.sort);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShowCornerMark()).compareTo(other.isSetShowCornerMark());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShowCornerMark()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.showCornerMark, other.showCornerMark);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TCarousel(");
    boolean first = true;

    sb.append("channelId:");
    sb.append(this.channelId);
    first = false;
    if (isSetImageUrl()) {
      if (!first) sb.append(", ");
      sb.append("imageUrl:");
      if (this.imageUrl == null) {
        sb.append("null");
      } else {
        sb.append(this.imageUrl);
      }
      first = false;
    }
    if (isSetSort()) {
      if (!first) sb.append(", ");
      sb.append("sort:");
      sb.append(this.sort);
      first = false;
    }
    if (isSetId()) {
      if (!first) sb.append(", ");
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetShowCornerMark()) {
      if (!first) sb.append(", ");
      sb.append("showCornerMark:");
      sb.append(this.showCornerMark);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TCarouselStandardSchemeFactory implements SchemeFactory {
    public TCarouselStandardScheme getScheme() {
      return new TCarouselStandardScheme();
    }
  }

  private static class TCarouselStandardScheme extends StandardScheme<TCarousel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TCarousel struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // CHANNEL_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.channelId = iprot.readI64();
              struct.setChannelIdIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IMAGE_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.imageUrl = iprot.readString();
              struct.setImageUrlIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sort = iprot.readI32();
              struct.setSortIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SHOW_CORNER_MARK
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.showCornerMark = iprot.readBool();
              struct.setShowCornerMarkIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TCarousel struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CHANNEL_ID_FIELD_DESC);
      oprot.writeI64(struct.channelId);
      oprot.writeFieldEnd();
      if (struct.imageUrl != null) {
        if (struct.isSetImageUrl()) {
          oprot.writeFieldBegin(IMAGE_URL_FIELD_DESC);
          oprot.writeString(struct.imageUrl);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetSort()) {
        oprot.writeFieldBegin(SORT_FIELD_DESC);
        oprot.writeI32(struct.sort);
        oprot.writeFieldEnd();
      }
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI64(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetShowCornerMark()) {
        oprot.writeFieldBegin(SHOW_CORNER_MARK_FIELD_DESC);
        oprot.writeBool(struct.showCornerMark);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TCarouselTupleSchemeFactory implements SchemeFactory {
    public TCarouselTupleScheme getScheme() {
      return new TCarouselTupleScheme();
    }
  }

  private static class TCarouselTupleScheme extends TupleScheme<TCarousel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TCarousel struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetChannelId()) {
        optionals.set(0);
      }
      if (struct.isSetImageUrl()) {
        optionals.set(1);
      }
      if (struct.isSetSort()) {
        optionals.set(2);
      }
      if (struct.isSetId()) {
        optionals.set(3);
      }
      if (struct.isSetShowCornerMark()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetChannelId()) {
        oprot.writeI64(struct.channelId);
      }
      if (struct.isSetImageUrl()) {
        oprot.writeString(struct.imageUrl);
      }
      if (struct.isSetSort()) {
        oprot.writeI32(struct.sort);
      }
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetShowCornerMark()) {
        oprot.writeBool(struct.showCornerMark);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TCarousel struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.channelId = iprot.readI64();
        struct.setChannelIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.imageUrl = iprot.readString();
        struct.setImageUrlIsSet(true);
      }
      if (incoming.get(2)) {
        struct.sort = iprot.readI32();
        struct.setSortIsSet(true);
      }
      if (incoming.get(3)) {
        struct.id = iprot.readI64();
        struct.setIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.showCornerMark = iprot.readBool();
        struct.setShowCornerMarkIsSet(true);
      }
    }
  }

}

