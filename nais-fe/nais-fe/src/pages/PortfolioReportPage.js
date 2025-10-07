import React, { useEffect, useState } from "react";
import axios from "axios";
import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer } from "recharts";
import { PieChart, Pie, Cell, Legend } from "recharts";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";



const COLORS = ["#8884d8", "#82ca9d", "#ff7300", "#0088FE", "#3399ff"];

const API_BASE = "http://localhost:8080";

export default function PortfolioReportPage() {

    const [productsByCategory, setProductsByCategory] = useState([]);
    const [variantsByProduct, setVariantsByProduct] = useState([]);
    const [topUpdated, setTopUpdated] = useState([]);
    const [updatesPerDay, setUpdatesPerDay] = useState([]);

     useEffect(() => {
    axios.get(`${API_BASE}/portfolio/products/count-by-category`)
      .then(res => setProductsByCategory(res.data))
      .catch(err => console.error(err));

    axios.get(`${API_BASE}/portfolio/variants/count-by-product`)
      .then(res => setVariantsByProduct(res.data))
      .catch(err => console.error(err));

    axios.get(`${API_BASE}/portfolio/product-history/top-updated-product-per-category`)
      .then(res => setTopUpdated(res.data))
      .catch(err => console.error(err));

    axios.get(`${API_BASE}/portfolio/product-history/updates-per-day`)
      .then(res => setUpdatesPerDay(res.data))
      .catch(err => console.error(err));
  }, []);

const exportPDF = () => {
  const input = document.getElementById("report-page");
  html2canvas(input, { scale: 2 }).then(canvas => {
    const imgData = canvas.toDataURL("image/png");
    const pdf = new jsPDF("p", "mm", "a4"); // portrait, A4
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = pdf.internal.pageSize.getHeight();

   
    const imgProps = pdf.getImageProperties(imgData);
    const imgWidth = pdfWidth;
    const imgHeight = (imgProps.height * pdfWidth) / imgProps.width;

    let heightLeft = imgHeight;
    let position = 0;

    pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
    heightLeft -= pdfHeight;

 
    while (heightLeft > 0) {
      position = heightLeft - imgHeight;
      pdf.addPage();
      pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
      heightLeft -= pdfHeight;
    }

    pdf.save("report.pdf");
    const pdfBlob = pdf.output("blob");
    const blobUrl = URL.createObjectURL(pdfBlob);
    window.open(blobUrl, "_blank");
  });
};


   return (<div>
     <button className="basic-btn" onClick={exportPDF}>Export to PDF</button>
    <div id="report-page"  className="portfolio-report-page">
       
      <h2>Products per Category</h2>

      <table>
      <thead>
        <tr>
          <th>Category ID</th>
          <th>Product Count</th>
        </tr>
      </thead>
      <tbody>
        {productsByCategory.map((row, index) => (
          <tr key={index}>
            <td>{row.category_id}</td>
            <td>{row.product_count}</td>
          </tr>
        ))}
      </tbody>
    </table>

      <ResponsiveContainer width="70%" height={300}>
  <PieChart>
    <Pie
      data={productsByCategory}
      dataKey="product_count"
      nameKey="category_id"
      cx="50%"
      cy="50%"
      outerRadius={100}
      fill="#8884d8"
      label
    >
      {productsByCategory.map((entry, index) => (
        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
      ))}
    </Pie>
    <Tooltip />
    <Legend />
  </PieChart>
</ResponsiveContainer>

      <h2>Variants per Product</h2>

         <table>
      <thead>
        <tr>
          <th>Product ID</th>
          <th>Variant Count</th>
        </tr>
      </thead>
      <tbody>
        {variantsByProduct.map((row, index) => (
          <tr key={index}>
            <td>{row.product_id}</td>
            <td>{row.variant_count}</td>
          </tr>
        ))}
      </tbody>
    </table>


      <ResponsiveContainer width="70%" height={300}>
  <PieChart>
    <Pie
      data={variantsByProduct}
      dataKey="variant_count"
      nameKey="product_id"
      cx="50%"
      cy="50%"
      outerRadius={100}
      fill="#82ca9d"
      label
    >
      {variantsByProduct.map((entry, index) => (
        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
      ))}
    </Pie>
    <Tooltip />
    <Legend />
  </PieChart>
</ResponsiveContainer>

      <h2>Top Updates per Category</h2>

      <table>
      <thead>
        <tr>
          <th>New Category ID</th>
          <th>Updates</th>
        </tr>
      </thead>
      <tbody>
        {topUpdated.map((row, index) => (
          <tr key={index}>
            <td>{row.new_category_id}</td>
            <td>{row.updates}</td>
          </tr>
        ))}
      </tbody>
    </table>


      <ResponsiveContainer width="70%" height={300}>
        <BarChart data={topUpdated}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="new_category_id" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="updates" fill="#ff9945ff" />
        </BarChart>
      </ResponsiveContainer>

      <h2>Updates per Day </h2>

       <table>
      <thead>
        <tr>
          <th>Day</th>
          <th>Updates</th>
        </tr>
      </thead>
      <tbody>
        {updatesPerDay.map((row, index) => (
          <tr key={index}>
            <td>{row.day}</td>
            <td>{row.updates}</td>
          </tr>
        ))}
      </tbody>
    </table>

      <ResponsiveContainer width="70%" height={300}>
        <BarChart data={updatesPerDay}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="day" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="updates" fill="#ebbfffff" />
        </BarChart>
      </ResponsiveContainer>
    </div>
    </div>
  );

}