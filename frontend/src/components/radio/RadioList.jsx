const frequencies = [
  { id: 1, name: "101.3 FM", desc: "힙합 & R&B" },
  { id: 2, name: "95.9 FM", desc: "인디 & 감성" },
  { id: 3, name: "107.7 FM", desc: "EDM & 파티" },
];

const RadioList = () => {
  return (
    <div className="radioList-box">
      <h3>📡 주파수 선택</h3>

      <ul>
        {frequencies.map((f) => (
          <li key={f.id} className="radioList-item">
            <strong>{f.name}</strong>
            <span>{f.desc}</span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default RadioList;
