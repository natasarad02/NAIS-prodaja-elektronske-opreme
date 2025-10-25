import React, { useState, useEffect, useRef, useCallback } from 'react';
import * as d3 from 'd3'; 
import { leadStatusService } from '../services/LeadStatusService'
import { leadLifecyclesService } from '../services/LeadLifecycleService'
import { complexQueryService } from '../services/ComplexLeadsService'
import { accountService } from '../services/AccountService'
import { leadService } from '../services/LeadService'
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

const LeadCountFilter = ({ onSearch }) => {
  const [value, setValue] = useState(0);

  const handleSearch = () => {
    onSearch(value); // poziva funkciju roditelja sa selektovanim brojem
  };

  return (
    <div className="flex items-center gap-4">
      <input
        type="range"
        min={0}
        max={10}
        value={value}
        onChange={(e) => setValue(Number(e.target.value))}
        className="w-64"
      />
      <span className="w-8 text-center">{value}</span>
      <button
        onClick={handleSearch}
        className="px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700 transition"
      >
        Search
      </button>
    </div>
  );
};

const LeadStatusSummaryChart = ({ data }) => {
  const ref = useRef();

  useEffect(() => {
    if (!data || data.length === 0) return;

    const svg = d3.select(ref.current);
    svg.selectAll("*").remove();

    const margin = { top: 40, right: 20, bottom: 50, left: 60 };
    const width = 800 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    const chart = svg
      .attr("viewBox", `0 0 ${width + margin.left + margin.right} ${height + margin.top + margin.bottom}`)
      .append("g")
      .attr("transform", `translate(${margin.left},${margin.top})`);

    // X osa (statusi)
    const x = d3.scaleBand()
      .domain(data.map(d => d.status))
      .range([0, width])
      .padding(0.2);

    chart.append("g")
      .attr("transform", `translate(0,${height})`)
      .call(d3.axisBottom(x));

    // Y osa (averageLeadAgeDays)
    const y = d3.scaleLinear()
      .domain([0, d3.max(data, d => d.averageLeadAgeDays)]).nice()
      .range([height, 0]);

    chart.append("g")
      .call(d3.axisLeft(y));

    // Boja barova (možeš koristiti interpolator ili fiksnu boju)
    const color = d3.scaleSequential()
      .domain([0, d3.max(data, d => d.averageLeadAgeDays)])
      .interpolator(d3.interpolateBlues);

    // Dodaj barove
    chart.selectAll("rect")
      .data(data)
      .join("rect")
      .attr("x", d => x(d.status))
      .attr("y", d => y(d.averageLeadAgeDays))
      .attr("height", d => height - y(d.averageLeadAgeDays))
      .attr("width", x.bandwidth())
      .attr("fill", d => color(d.averageLeadAgeDays));

    // Dodaj tekst iznad barova (totalLeads + oldestLeadCreatedAt)
    chart.selectAll("text.label")
      .data(data)
      .join("text")
      .attr("class", "label")
      .attr("x", d => x(d.status) + x.bandwidth()/2)
      .attr("y", d => y(d.averageLeadAgeDays) - 5)
      .attr("text-anchor", "middle")
      .style("font-size", "12px")
      .style("fill", "#333")
      .text(d => `${d.totalLeads} | ${new Date(d.oldestLeadCreatedAt).toLocaleDateString()}`);
      
    // Nakon definicije color skale i dodavanja barova, dodaj ovo:
    const legendWidth = 200;
    const legendHeight = 10;

    // Grupa za legendu
    const legendGroup = chart.append("g")
    .attr("transform", `translate(${width - legendWidth - 10}, -30)`);

    // Gradient definicija
    const defs = svg.append("defs");
    const linearGradient = defs.append("linearGradient")
    .attr("id", "legend-gradient");

    linearGradient.selectAll("stop")
    .data([
        { offset: "0%", color: d3.interpolateBlues(0) },
        { offset: "100%", color: d3.interpolateBlues(1) }
    ])
    .enter()
    .append("stop")
    .attr("offset", d => d.offset)
    .attr("stop-color", d => d.color);

    // Pravougaonik koji koristi gradient
    legendGroup.append("rect")
    .attr("width", legendWidth)
    .attr("height", legendHeight)
    .style("fill", "url(#legend-gradient)");

    // Legenda osa
    const legendScale = d3.scaleLinear()
    .domain([0, d3.max(data, d => d.averageLeadAgeDays)])
    .range([0, legendWidth]);

    const legendAxis = d3.axisBottom(legendScale)
    .ticks(5)
    .tickFormat(d => d + " days");

    legendGroup.append("g")
    .attr("transform", `translate(0,${legendHeight})`)
    .call(legendAxis);

    // Legend tekst
    legendGroup.append("text")
    .attr("x", 0)
    .attr("y", -5)
    .attr("font-size", "12px")
    .attr("fill", "#000")
    .text("Average duration of a lead status(days)");
    }, [data]);

  return <svg ref={ref} className="mx-auto max-w-[1000px] w-full h-[400px]"
    style={{
        maxWidth: '1200px'
    }}
  ></svg>;
};


// --- Pomoćne Komponente za Prikaz ---

/**
 * Kontejner sekcije - Definiše stil za sve kartice izveštaja.
 */
const Section = ({ title, children }) => (
    <section className="bg-white p-6 rounded-xl shadow-2xl transition duration-300 hover:shadow-indigo-300 hover:scale-[1.005]">
        <h2 className="text-3xl font-extrabold text-gray-900 border-b-4 border-indigo-500 pb-3 mb-6">
            {title}
        </h2>
        {children}
    </section>
);

/**
 * Prikazuje podatke u tabelarnom formatu.
 */
const DataTable = ({ data, headers }) => {
    if (!data || data.length === 0) {
        return <p className="text-center text-red-600 p-4 bg-red-100 rounded-lg">Nema pronađenih slogova za prikaz.</p>;
    }

    const formatValue = (key, value) => {
        if (key.includes('CreatedAt') && typeof value === 'string') {
            const date = new Date(value);
            return date.toLocaleDateString('sr-RS') + ' ' + date.toLocaleTimeString('sr-RS');
        }
        if (key.includes('AverageLeadsPerContact') && typeof value === 'number') {
            return value.toFixed(1);
        }
        return value;
    };

    const keys = Object.keys(data[0]);

    return (
        <div className="overflow-x-auto" style={{display: "flex", justifyContent: "center"}}> 
            <table style={{maxWidth: '1000px'}}
            className="divide-y divide-gray-200 shadow-lg rounded-xl overflow-hidden mx-auto max-w-[1000px] w-full"> 
                <thead className="bg-indigo-600">
                    <tr>
                        {headers.map((header, index) => (
                            <th key={index} className="px-6 py-3 text-left text-xs font-bold text-white uppercase tracking-wider">
                                {header}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                    {data.map((row, rowIndex) => (
                        <tr key={rowIndex} className="hover:bg-indigo-50 transition duration-150">
                            {keys.map((key, cellIndex) => (
                                <td key={cellIndex} className="px-6 py-4 whitespace-nowrap text-sm text-gray-800">
                                    {formatValue(key, row[key])}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

// --- Glavna Komponenta Stranice ---

export const LeadReportPage = () => {
    const [reportData, setReportData] = useState({
        ranking: [],
        summary: [],
        density: [],
        update: [],
        deletion: null
    });
    const [isLoading, setIsLoading] = useState(true);
    const [statuses, setStatuses] = useState([]);
    const [lifecycles, setLifecycles] = useState([]);
    const [state1, setState1] = useState('');
    const [state2, setState2] = useState('');
    const [selectedLifecycle, setSelectedLifecycle] = useState('');
    const [selectedLifecycle2, setSelectedLifecycle2] = useState('');

    useEffect(() => {
        const loadData = async () => {
            try {
                getLeadStatuses();
                getLeadlIfecycles();
            } catch (error) {
                console.error("Greška pri učitavanju podataka izveštaja:", error);
            } finally {
                setIsLoading(false);
            }
        };
        loadData();
    }, []);

    function getLeadStatuses() {
        leadStatusService.getAll()
        .then(response => {
            setStatuses(response);
        })
    }

    function getLeadlIfecycles() {
        leadLifecyclesService.getAll()
        .then(response => {
            setLifecycles(response);
        })
    }

    function getAccountRanging() {
        if(!state1) return
        complexQueryService.fetchAccountRanking(state1)
        .then(response => {
            console.log(response)
            setReportData(prev => ({
                ...prev,
                ranking: response,
            }));
        })
    }

    function getLifecycleSummery() {
        if(!selectedLifecycle) return;
        console.log(selectedLifecycle)
        complexQueryService.fetchLeadStatusSummary(selectedLifecycle)
        .then(response => {
            console.log(response)
            setReportData(prev => ({
                ...prev,
                summary: response,
            }));
        })
    }

    function getAccountsWithMoreThanNLeads(number) {
        accountService.getProsti(number)
        .then(res => {
            const filtered = res.map(item => ({
                id: item.id,
                name: item.name,
                email: item.email,
                phone: item.phone
            }));

            setReportData(prev => ({
                ...prev,
                update: filtered,
            }));
        })
        .catch(err => {
            console.log(err)
        })
    }

    const generatePDF = async () => {
        const report = document.getElementById("report-container");
        const rect = report.getBoundingClientRect();

        // Centralnih 1200px + dodatnih 150px desno
        const desiredWidth = 1200;
        const extraRight = 150;
        const contentWidth = Math.min(rect.width, desiredWidth + extraRight);
        const contentHeight = rect.height;

        // Offset za centralnih 1200px (centriranje)
        let offsetX = 0;
        if (rect.width > desiredWidth) {
            offsetX = (rect.width - desiredWidth) / 2;
        }

        // Render canvas-a
        const canvas = await html2canvas(report, {
            scale: 2,
            width: contentWidth,
            height: contentHeight,
            x: offsetX,
            y: 0,
            windowWidth: document.body.scrollWidth,
            windowHeight: document.body.scrollHeight
        });

        const pdf = new jsPDF({
            orientation: "portrait",
            unit: "px",
            format: [595, 842] // A4 size
        });

        const pdfWidth = 595;
        const pdfHeight = 842;
        const scale = pdfWidth / canvas.width; // proporcija za A4

        let position = 0;

        while (position < canvas.height) {
            const pageCanvas = document.createElement("canvas");
            pageCanvas.width = canvas.width;
            pageCanvas.height = Math.min(pdfHeight / scale, canvas.height - position);

            const ctx = pageCanvas.getContext("2d");
            ctx.drawImage(
                canvas,
                0,
                position,
                canvas.width,
                pageCanvas.height,
                0,
                0,
                canvas.width,
                pageCanvas.height
            );

            const imgData = pageCanvas.toDataURL("image/png");
            if (position > 0) pdf.addPage();
            pdf.addImage(imgData, "PNG", 0, 0, pdfWidth, pageCanvas.height * scale);

            position += pageCanvas.height;
        }

        pdf.save("lead-report.pdf");
    };

    if (isLoading) {
        return (
            <div className="flex justify-center items-center h-screen bg-gray-100">
                <div className="text-xl font-semibold text-indigo-600 p-6 bg-white rounded-xl shadow-xl">
                    <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-indigo-500 inline" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    Učitavanje kompleksnih uvida...
                </div>
            </div>
        );
    }

    function getLeadsWithStatus() {
        if(!selectedLifecycle2) return;
        leadService.getProsti(selectedLifecycle2)
        .then(res => {
            const filtered = res.map(item => ({
                id: item.id,
                description: item.description,
                createdAt: item.createdAt
            }));

            setReportData(prev => ({
                ...prev,
                density: filtered,
            }));
        })
        .catch(err => {
            console.log(err)
        })
    }

    return (
        // GLAVNI LAYOUT KONTEJNER: Odgovoran za horizontalno centriranje celog sadržaja na stranici.
        <div className="flex flex-col items-center p-4 sm:p-8 bg-gray-100 min-h-screen">

            {/* Dugme za generisanje PDF-a */}
            <button
                onClick={generatePDF}
                className="mb-6 px-4 py-2 bg-indigo-600 text-white font-bold rounded hover:bg-indigo-700 transition"
            >
                Generiši PDF izveštaj
            </button>

            {/* MAIN KONTEJNER: Ograničavamo širinu (max-w-7xl) i centriramo ga (mx-auto). */}
            <div id="report-container" className="w-full max-w-7xl mx-auto space-y-12" >
                
                <Section title="1. Ranking Accounts">
                    <p className="text-gray-600 mb-6">Top Accounts by the number of leads in the selected status.</p>
                    <div>
                        <select style={{marginRight: '1rem'}}
                            value={state1}
                            onChange={(e) => setState1(e.target.value)}>
                            <option>Select Lead Status</option>
                            {statuses.map((status) => (
                                <option key={status.id} value={status.name}>
                                    {status.name}
                                </option>
                            ))}
                        </select>
                        <button onClick={() => getAccountRanging()} disabled={!state1}>
                            Select Lead Status
                        </button>
                    </div>
                    <DataTable
                        data={reportData.ranking} 
                        headers={["Account Name", "Total Number of Contacts", "Qualified Leads"]}
                    />
                </Section>

                <Section title="2. Lifecycle's Statuses Analysis">
                    <p className="text-gray-600 mb-6">
                        Shows lead status summary for the selected lead lifecycle.
                    </p>
                    <div>
                        <select style={{marginRight: '1rem'}}
                            value={selectedLifecycle}
                            onChange={(e) => setSelectedLifecycle(e.target.value)}>
                            <option>Select Lead Lifecycle</option>
                            {lifecycles.map((lifecycle) => (
                                <option key={lifecycle.id} value={lifecycle.name}>
                                    {lifecycle.name}
                                </option>
                            ))}
                        </select>
                        <button onClick={() => getLifecycleSummery()} disabled={!selectedLifecycle}>
                            Select Lead lifecycle
                        </button>
                    </div>
                    <LeadStatusSummaryChart data={reportData.summary} />
                </Section>

                <Section title="3. Leads With Selected Status">
                    <p className="text-gray-600 mb-6">
                        Shows lead that are in selected status.
                    </p>
                    <div>
                        <select style={{marginRight: '1rem'}}
                            value={selectedLifecycle2}
                            onChange={(e) => setSelectedLifecycle2(e.target.value)}>
                            <option>Select Lead Lifecycle</option>
                            {statuses.map((lifecycle) => (
                                <option key={lifecycle.id} value={lifecycle.id}>
                                    {lifecycle.name}
                                </option>
                            ))}
                        </select>
                        <button onClick={() => getLeadsWithStatus()} disabled={!selectedLifecycle2}>
                            Select Lead Status
                        </button>
                    </div>
                    <DataTable
                        data={reportData.density} 
                        headers={["Lead ID", "Description", "Creation Time"]}
                    />
                </Section>

                <Section title="4. Accounts with a certain number of Leads">
                    <p className="text-gray-600 mb-6">
                        Shows Accounts with more then n leads.
                    </p>
                    <LeadCountFilter onSearch={(selectedNumber) => {
                        console.log("Tražimo Accounts sa više od:", selectedNumber);
                        getAccountsWithMoreThanNLeads(selectedNumber); // tvoja funkcija
                    }} />
                    <DataTable
                        data={reportData.update} 
                        headers={["Account ID", "Name", "Email", "Phone Number"]}
                    />
                </Section>
            </div>
        </div>
    );
};

export default LeadReportPage;
