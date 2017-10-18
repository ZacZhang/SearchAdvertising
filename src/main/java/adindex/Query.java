// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SelectAds.proto

package adindex;

/**
 * Protobuf type {@code adindex.Query}
 */
public  final class Query extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:adindex.Query)
        QueryOrBuilder {
    // Use Query.newBuilder() to construct.
    private Query(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }
    private Query() {
        term_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
        return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Query(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        int mutable_bitField0_ = 0;
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    default: {
                        if (!input.skipField(tag)) {
                            done = true;
                        }
                        break;
                    }
                    case 10: {
                        java.lang.String s = input.readStringRequireUtf8();
                        if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                            term_ = new com.google.protobuf.LazyStringArrayList();
                            mutable_bitField0_ |= 0x00000001;
                        }
                        term_.add(s);
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
                term_ = term_.getUnmodifiableView();
            }
            makeExtensionsImmutable();
        }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
        return io.bittiger.adindex.SearchAds.internal_static_adindex_Query_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
        return io.bittiger.adindex.SearchAds.internal_static_adindex_Query_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        io.bittiger.adindex.Query.class, io.bittiger.adindex.Query.Builder.class);
    }

    public static final int TERM_FIELD_NUMBER = 1;
    private com.google.protobuf.LazyStringList term_;
    /**
     * <code>repeated string term = 1;</code>
     */
    public com.google.protobuf.ProtocolStringList
    getTermList() {
        return term_;
    }
    /**
     * <code>repeated string term = 1;</code>
     */
    public int getTermCount() {
        return term_.size();
    }
    /**
     * <code>repeated string term = 1;</code>
     */
    public java.lang.String getTerm(int index) {
        return term_.get(index);
    }
    /**
     * <code>repeated string term = 1;</code>
     */
    public com.google.protobuf.ByteString
    getTermBytes(int index) {
        return term_.getByteString(index);
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
        for (int i = 0; i < term_.size(); i++) {
            com.google.protobuf.GeneratedMessageV3.writeString(output, 1, term_.getRaw(i));
        }
    }

    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        {
            int dataSize = 0;
            for (int i = 0; i < term_.size(); i++) {
                dataSize += computeStringSizeNoTag(term_.getRaw(i));
            }
            size += dataSize;
            size += 1 * getTermList().size();
        }
        memoizedSize = size;
        return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof io.bittiger.adindex.Query)) {
            return super.equals(obj);
        }
        io.bittiger.adindex.Query other = (io.bittiger.adindex.Query) obj;

        boolean result = true;
        result = result && getTermList()
                .equals(other.getTermList());
        return result;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        if (getTermCount() > 0) {
            hash = (37 * hash) + TERM_FIELD_NUMBER;
            hash = (53 * hash) + getTermList().hashCode();
        }
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static io.bittiger.adindex.Query parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }
    public static io.bittiger.adindex.Query parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.bittiger.adindex.Query parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }
    public static io.bittiger.adindex.Query parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.bittiger.adindex.Query parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }
    public static io.bittiger.adindex.Query parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.bittiger.adindex.Query parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }
    public static io.bittiger.adindex.Query parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static io.bittiger.adindex.Query parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }
    public static io.bittiger.adindex.Query parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static io.bittiger.adindex.Query parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }
    public static io.bittiger.adindex.Query parseFrom(
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
    public static Builder newBuilder(io.bittiger.adindex.Query prototype) {
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
     * Protobuf type {@code adindex.Query}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:adindex.Query)
            io.bittiger.adindex.QueryOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return io.bittiger.adindex.SearchAds.internal_static_adindex_Query_descriptor;
        }

        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return io.bittiger.adindex.SearchAds.internal_static_adindex_Query_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            io.bittiger.adindex.Query.class, io.bittiger.adindex.Query.Builder.class);
        }

        // Construct using io.bittiger.adindex.Query.newBuilder()
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
            }
        }
        public Builder clear() {
            super.clear();
            term_ = com.google.protobuf.LazyStringArrayList.EMPTY;
            bitField0_ = (bitField0_ & ~0x00000001);
            return this;
        }

        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return io.bittiger.adindex.SearchAds.internal_static_adindex_Query_descriptor;
        }

        public io.bittiger.adindex.Query getDefaultInstanceForType() {
            return io.bittiger.adindex.Query.getDefaultInstance();
        }

        public io.bittiger.adindex.Query build() {
            io.bittiger.adindex.Query result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        public io.bittiger.adindex.Query buildPartial() {
            io.bittiger.adindex.Query result = new io.bittiger.adindex.Query(this);
            int from_bitField0_ = bitField0_;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                term_ = term_.getUnmodifiableView();
                bitField0_ = (bitField0_ & ~0x00000001);
            }
            result.term_ = term_;
            onBuilt();
            return result;
        }

        public Builder clone() {
            return (Builder) super.clone();
        }
        public Builder setField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                Object value) {
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
                int index, Object value) {
            return (Builder) super.setRepeatedField(field, index, value);
        }
        public Builder addRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                Object value) {
            return (Builder) super.addRepeatedField(field, value);
        }
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof io.bittiger.adindex.Query) {
                return mergeFrom((io.bittiger.adindex.Query)other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(io.bittiger.adindex.Query other) {
            if (other == io.bittiger.adindex.Query.getDefaultInstance()) return this;
            if (!other.term_.isEmpty()) {
                if (term_.isEmpty()) {
                    term_ = other.term_;
                    bitField0_ = (bitField0_ & ~0x00000001);
                } else {
                    ensureTermIsMutable();
                    term_.addAll(other.term_);
                }
                onChanged();
            }
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
            io.bittiger.adindex.Query parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (io.bittiger.adindex.Query) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }
        private int bitField0_;

        private com.google.protobuf.LazyStringList term_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        private void ensureTermIsMutable() {
            if (!((bitField0_ & 0x00000001) == 0x00000001)) {
                term_ = new com.google.protobuf.LazyStringArrayList(term_);
                bitField0_ |= 0x00000001;
            }
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public com.google.protobuf.ProtocolStringList
        getTermList() {
            return term_.getUnmodifiableView();
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public int getTermCount() {
            return term_.size();
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public java.lang.String getTerm(int index) {
            return term_.get(index);
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public com.google.protobuf.ByteString
        getTermBytes(int index) {
            return term_.getByteString(index);
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public Builder setTerm(
                int index, java.lang.String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ensureTermIsMutable();
            term_.set(index, value);
            onChanged();
            return this;
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public Builder addTerm(
                java.lang.String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ensureTermIsMutable();
            term_.add(value);
            onChanged();
            return this;
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public Builder addAllTerm(
                java.lang.Iterable<java.lang.String> values) {
            ensureTermIsMutable();
            com.google.protobuf.AbstractMessageLite.Builder.addAll(
                    values, term_);
            onChanged();
            return this;
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public Builder clearTerm() {
            term_ = com.google.protobuf.LazyStringArrayList.EMPTY;
            bitField0_ = (bitField0_ & ~0x00000001);
            onChanged();
            return this;
        }
        /**
         * <code>repeated string term = 1;</code>
         */
        public Builder addTermBytes(
                com.google.protobuf.ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            checkByteStringIsUtf8(value);
            ensureTermIsMutable();
            term_.add(value);
            onChanged();
            return this;
        }
        public final Builder setUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return this;
        }

        public final Builder mergeUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return this;
        }


        // @@protoc_insertion_point(builder_scope:adindex.Query)
    }

    // @@protoc_insertion_point(class_scope:adindex.Query)
    private static final io.bittiger.adindex.Query DEFAULT_INSTANCE;
    static {
        DEFAULT_INSTANCE = new io.bittiger.adindex.Query();
    }

    public static io.bittiger.adindex.Query getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Query>
            PARSER = new com.google.protobuf.AbstractParser<Query>() {
        public Query parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return new Query(input, extensionRegistry);
        }
    };

    public static com.google.protobuf.Parser<Query> parser() {
        return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Query> getParserForType() {
        return PARSER;
    }

    public io.bittiger.adindex.Query getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

