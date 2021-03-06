// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SelectAds.proto

package adindex;

/**
 * <pre>
 * ads web server -&gt; ads index server
 * </pre>
 *
 * Protobuf type {@code adindex.AdsRequest}
 */
public  final class AdsRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:adindex.AdsRequest)
    AdsRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AdsRequest.newBuilder() to construct.
  private AdsRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AdsRequest() {
    query_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AdsRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              query_ = new java.util.ArrayList<adindex.Query>();
              mutable_bitField0_ |= 0x00000001;
            }
            query_.add(
                input.readMessage(adindex.Query.parser(), extensionRegistry));
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        query_ = java.util.Collections.unmodifiableList(query_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return adindex.SearchAdvertising.internal_static_adindex_AdsRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return adindex.SearchAdvertising.internal_static_adindex_AdsRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            adindex.AdsRequest.class, adindex.AdsRequest.Builder.class);
  }

  public static final int QUERY_FIELD_NUMBER = 1;
  private java.util.List<adindex.Query> query_;
  /**
   * <code>repeated .adindex.Query query = 1;</code>
   */
  public java.util.List<adindex.Query> getQueryList() {
    return query_;
  }
  /**
   * <code>repeated .adindex.Query query = 1;</code>
   */
  public java.util.List<? extends adindex.QueryOrBuilder> 
      getQueryOrBuilderList() {
    return query_;
  }
  /**
   * <code>repeated .adindex.Query query = 1;</code>
   */
  public int getQueryCount() {
    return query_.size();
  }
  /**
   * <code>repeated .adindex.Query query = 1;</code>
   */
  public adindex.Query getQuery(int index) {
    return query_.get(index);
  }
  /**
   * <code>repeated .adindex.Query query = 1;</code>
   */
  public adindex.QueryOrBuilder getQueryOrBuilder(
      int index) {
    return query_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < query_.size(); i++) {
      output.writeMessage(1, query_.get(i));
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < query_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, query_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof adindex.AdsRequest)) {
      return super.equals(obj);
    }
    adindex.AdsRequest other = (adindex.AdsRequest) obj;

    boolean result = true;
    result = result && getQueryList()
        .equals(other.getQueryList());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getQueryCount() > 0) {
      hash = (37 * hash) + QUERY_FIELD_NUMBER;
      hash = (53 * hash) + getQueryList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static adindex.AdsRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static adindex.AdsRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static adindex.AdsRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static adindex.AdsRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static adindex.AdsRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static adindex.AdsRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static adindex.AdsRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static adindex.AdsRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static adindex.AdsRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static adindex.AdsRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static adindex.AdsRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static adindex.AdsRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(adindex.AdsRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * ads web server -&gt; ads index server
   * </pre>
   *
   * Protobuf type {@code adindex.AdsRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:adindex.AdsRequest)
      adindex.AdsRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return adindex.SearchAdvertising.internal_static_adindex_AdsRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return adindex.SearchAdvertising.internal_static_adindex_AdsRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              adindex.AdsRequest.class, adindex.AdsRequest.Builder.class);
    }

    // Construct using adindex.AdsRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getQueryFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (queryBuilder_ == null) {
        query_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        queryBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return adindex.SearchAdvertising.internal_static_adindex_AdsRequest_descriptor;
    }

    public adindex.AdsRequest getDefaultInstanceForType() {
      return adindex.AdsRequest.getDefaultInstance();
    }

    public adindex.AdsRequest build() {
      adindex.AdsRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public adindex.AdsRequest buildPartial() {
      adindex.AdsRequest result = new adindex.AdsRequest(this);
      int from_bitField0_ = bitField0_;
      if (queryBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          query_ = java.util.Collections.unmodifiableList(query_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.query_ = query_;
      } else {
        result.query_ = queryBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof adindex.AdsRequest) {
        return mergeFrom((adindex.AdsRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(adindex.AdsRequest other) {
      if (other == adindex.AdsRequest.getDefaultInstance()) return this;
      if (queryBuilder_ == null) {
        if (!other.query_.isEmpty()) {
          if (query_.isEmpty()) {
            query_ = other.query_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureQueryIsMutable();
            query_.addAll(other.query_);
          }
          onChanged();
        }
      } else {
        if (!other.query_.isEmpty()) {
          if (queryBuilder_.isEmpty()) {
            queryBuilder_.dispose();
            queryBuilder_ = null;
            query_ = other.query_;
            bitField0_ = (bitField0_ & ~0x00000001);
            queryBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getQueryFieldBuilder() : null;
          } else {
            queryBuilder_.addAllMessages(other.query_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      adindex.AdsRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (adindex.AdsRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<adindex.Query> query_ =
      java.util.Collections.emptyList();
    private void ensureQueryIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        query_ = new java.util.ArrayList<adindex.Query>(query_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        adindex.Query, adindex.Query.Builder, adindex.QueryOrBuilder> queryBuilder_;

    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public java.util.List<adindex.Query> getQueryList() {
      if (queryBuilder_ == null) {
        return java.util.Collections.unmodifiableList(query_);
      } else {
        return queryBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public int getQueryCount() {
      if (queryBuilder_ == null) {
        return query_.size();
      } else {
        return queryBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public adindex.Query getQuery(int index) {
      if (queryBuilder_ == null) {
        return query_.get(index);
      } else {
        return queryBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder setQuery(
        int index, adindex.Query value) {
      if (queryBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureQueryIsMutable();
        query_.set(index, value);
        onChanged();
      } else {
        queryBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder setQuery(
        int index, adindex.Query.Builder builderForValue) {
      if (queryBuilder_ == null) {
        ensureQueryIsMutable();
        query_.set(index, builderForValue.build());
        onChanged();
      } else {
        queryBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder addQuery(adindex.Query value) {
      if (queryBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureQueryIsMutable();
        query_.add(value);
        onChanged();
      } else {
        queryBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder addQuery(
        int index, adindex.Query value) {
      if (queryBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureQueryIsMutable();
        query_.add(index, value);
        onChanged();
      } else {
        queryBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder addQuery(
        adindex.Query.Builder builderForValue) {
      if (queryBuilder_ == null) {
        ensureQueryIsMutable();
        query_.add(builderForValue.build());
        onChanged();
      } else {
        queryBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder addQuery(
        int index, adindex.Query.Builder builderForValue) {
      if (queryBuilder_ == null) {
        ensureQueryIsMutable();
        query_.add(index, builderForValue.build());
        onChanged();
      } else {
        queryBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder addAllQuery(
        java.lang.Iterable<? extends adindex.Query> values) {
      if (queryBuilder_ == null) {
        ensureQueryIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, query_);
        onChanged();
      } else {
        queryBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder clearQuery() {
      if (queryBuilder_ == null) {
        query_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        queryBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public Builder removeQuery(int index) {
      if (queryBuilder_ == null) {
        ensureQueryIsMutable();
        query_.remove(index);
        onChanged();
      } else {
        queryBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public adindex.Query.Builder getQueryBuilder(
        int index) {
      return getQueryFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public adindex.QueryOrBuilder getQueryOrBuilder(
        int index) {
      if (queryBuilder_ == null) {
        return query_.get(index);  } else {
        return queryBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public java.util.List<? extends adindex.QueryOrBuilder> 
         getQueryOrBuilderList() {
      if (queryBuilder_ != null) {
        return queryBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(query_);
      }
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public adindex.Query.Builder addQueryBuilder() {
      return getQueryFieldBuilder().addBuilder(
          adindex.Query.getDefaultInstance());
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public adindex.Query.Builder addQueryBuilder(
        int index) {
      return getQueryFieldBuilder().addBuilder(
          index, adindex.Query.getDefaultInstance());
    }
    /**
     * <code>repeated .adindex.Query query = 1;</code>
     */
    public java.util.List<adindex.Query.Builder> 
         getQueryBuilderList() {
      return getQueryFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        adindex.Query, adindex.Query.Builder, adindex.QueryOrBuilder> 
        getQueryFieldBuilder() {
      if (queryBuilder_ == null) {
        queryBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            adindex.Query, adindex.Query.Builder, adindex.QueryOrBuilder>(
                query_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        query_ = null;
      }
      return queryBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:adindex.AdsRequest)
  }

  // @@protoc_insertion_point(class_scope:adindex.AdsRequest)
  private static final adindex.AdsRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new adindex.AdsRequest();
  }

  public static adindex.AdsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AdsRequest>
      PARSER = new com.google.protobuf.AbstractParser<AdsRequest>() {
    public AdsRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new AdsRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AdsRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AdsRequest> getParserForType() {
    return PARSER;
  }

  public adindex.AdsRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

