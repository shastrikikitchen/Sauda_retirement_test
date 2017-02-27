package com.traveller.enthusiastic.networkUtils.Response;

import com.traveller.enthusiastic.networkUtils.ValueObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sauda on 15/02/17.
 */

public class DemoResponse  extends ValueObject{
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payload implements Serializable {
        private int gridTotalCount;
        private List<InventoryHistory> inventoryHistory;
        private List<PricingHistory> pricingHistory;

        public int getGridTotalCount() {
            return gridTotalCount;
        }

        public void setGridTotalCount(int gridTotalCount) {
            this.gridTotalCount = gridTotalCount;
        }

        public List<InventoryHistory> getInventoryHistory() {
            return inventoryHistory;
        }

        public void setInventoryHistory(List<InventoryHistory> inventoryHistory) {
            this.inventoryHistory = inventoryHistory;
        }

        public List<PricingHistory> getPricingHistory() {
            return pricingHistory;
        }

        public void setPricingHistory(List<PricingHistory> pricingHistory) {
            this.pricingHistory = pricingHistory;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class InventoryHistory implements Serializable {
            private String productName;
            private String imageUrl;
            private Long updateTime;
            private String supc;
            private String sellerSku;
            private String status;
            private String comment;
            private String oldValue;
            private String newValue;
            private List<Attributes> attributes;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public List<Attributes> getAttributes() {
                return attributes;
            }

            public void setAttributes(List<Attributes> attributes) {
                this.attributes = attributes;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public Long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Long updateTime) {
                this.updateTime = updateTime;
            }

            public String getSupc() {
                return supc;
            }

            public void setSupc(String supc) {
                this.supc = supc;
            }

            public String getSellerSku() {
                return sellerSku;
            }

            public void setSellerSku(String sellerSku) {
                this.sellerSku = sellerSku;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOldValue() {
                return oldValue;
            }

            public void setOldValue(String oldValue) {
                this.oldValue = oldValue;
            }

            public String getNewValue() {
                return newValue;
            }

            public void setNewValue(String newValue) {
                this.newValue = newValue;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Attributes implements Serializable {
                private String name;
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PricingHistory implements Serializable {
            private String productName;
            private String imageUrl;
            private Long modifiedOn;
            private String supc;
            private String sellerSku;
            private String status;
            private String comment;
            private List<Attributes> attributes;
            private List<LineItems> lineItems;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public Long getModifiedOn() {
                return modifiedOn;
            }

            public void setModifiedOn(Long modifiedOn) {
                this.modifiedOn = modifiedOn;
            }

            public String getSupc() {
                return supc;
            }

            public void setSupc(String supc) {
                this.supc = supc;
            }

            public String getSellerSku() {
                return sellerSku;
            }

            public void setSellerSku(String sellerSku) {
                this.sellerSku = sellerSku;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<Attributes> getAttributes() {
                return attributes;
            }

            public void setAttributes(List<Attributes> attributes) {
                this.attributes = attributes;
            }

            public List<LineItems> getLineItems() {
                return lineItems;
            }

            public void setLineItems(List<LineItems> lineItems) {
                this.lineItems = lineItems;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class LineItems implements Serializable {
                private String oldValue;
                private String newValue;
                private String changedField;

                public String getOldValue() {
                    return oldValue;
                }

                public void setOldValue(String oldValue) {
                    this.oldValue = oldValue;
                }

                public String getNewValue() {
                    return newValue;
                }

                public void setNewValue(String newValue) {
                    this.newValue = newValue;
                }

                public String getChangedField() {
                    return changedField;
                }

                public void setChangedField(String changedField) {
                    this.changedField = changedField;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Attributes implements Serializable {
                private String name;
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }
        }
    }

}
