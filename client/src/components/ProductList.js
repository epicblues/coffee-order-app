import React from "react";
import { Product } from "./Product";

export const ProductList = ({ productDatas = [], onAddsClick }) => (
  <>
    <h5 className="flex-grow-0">
      <b>상품 목록</b>
    </h5>
    <ul className="list-group products">
      {productDatas.map((data) => (
        <Product key={data.productId} {...data} onAddsClick={onAddsClick} />
      ))}
    </ul>
  </>
);
