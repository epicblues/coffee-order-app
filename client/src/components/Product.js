import React from "react";

export const Product = ({
  productId,
  productName,
  category,
  price,
  onAddsClick,
}) => {
  const handleAddBtnClicked = () => {
    onAddsClick(productId);
  };
  return (
    <>
      <li className="list-group-item d-flex mt-2">
        <div className="col-2">
          <img
            className="img-fluid"
            src="https://i.imgur.com/HKOFQYa.jpeg"
            alt=""
          />
        </div>
        <div className="col">
          <div className="row text-muted">{productName}</div>
          <div className="row">{category}</div>
        </div>
        <div className="col text-center price">{price}원</div>
        <div className="col text-end action">
          <button
            className="btn btn-small btn-outline-dark"
            onClick={handleAddBtnClicked}
            href=""
          >
            추가
          </button>
        </div>
      </li>
    </>
  );
};
