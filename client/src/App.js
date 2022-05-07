import "./App.css";
import "bootstrap/dist/css/bootstrap.css";
import React, {useEffect, useState} from "react";
import {ProductList} from "./components/ProductList";
import {Summary} from "./components/Summary";
import axios from "axios";

function App() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    // side effect로 비동기 통신을 하는 것이 좋다.
    (async () => {
      const {data} = await axios.get("http://localhost:8080/api/v1/products");
      setProducts(data);
    })();
  }, []);

  const handleOrderSubmit = async (order) => {
    if (items.length === 0) {
      throw new Error("아이템을 추가해 주세요!");
    }
    const postData = {
      ...order,
      orderItems: items.map((item) => ({
        productId: item.productId,
        category: item.category,
        price: item.price,
        quantity: item.count,
      })),
    };

    const {data: orderResult} = await axios.post(
        "http://localhost:8080/api/v1/orders",
        postData
    );
    setItems([]);
  };

  const onAddsClick = (productId) => {
    const product = products.find((product) => product.productId === productId);
    const found = items.find((item) => item.productId === productId);
    const updatedItems = found
        ? items.map((item) =>
            item.productId === productId
                ? {...item, count: item.count + 1}
                : item
        )
        : [...items, {...product, count: 1}];
    setItems(updatedItems);
  };

  const [items, setItems] = useState([]);
  return (
      <div className="container-fluid">
        <div className="row justify-content-center m-4">
          <h1 className="text-center">BloodEye Coffee</h1>
        </div>
        <div className="card">
          <div className="row">
            <div
                className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
              <ProductList productDatas={products} onAddsClick={onAddsClick}/>
            </div>
            <div className="col-md-4 summary p-4">
              <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
            </div>
          </div>
        </div>
      </div>
  );
}

export default App;
