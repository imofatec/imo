export default function SkeletonIndex() {
    return (
      <div className="bg-custom-dark-purple min-h-screen px-4 md:px-8 animate-pulse">
        <div className="h-10 w-1/4 bg-gray-700 rounded mt-8 mb-8" />
  
        {[1, 2, 3].map((_, i) => (
          <div key={i} className="flex flex-col gap-6 mb-8">
            <div
              className={`flex flex-col md:flex-row ${
                i === 1 ? 'md:flex-row-reverse' : ''
              } items-center justify-between gap-8`}
            >
              <div className="flex-1 space-y-4">
                <div className="h-6 w-1/2 bg-gray-700 rounded" />
                <div className="h-4 w-full bg-gray-700 rounded" />
                <div className="h-4 w-5/6 bg-gray-700 rounded" />
                <div className="h-4 w-4/6 bg-gray-700 rounded" />
                <div className="h-4 w-3/5 bg-gray-700 rounded" />
                <div className="h-4 w-2/3 bg-gray-700 rounded" />
              </div>
              <div className="w-full md:w-[400px] h-[250px] bg-gray-700 rounded-lg" />
            </div>
            <div className="h-[1px] bg-white w-full max-w-[900px] m-auto my-4" />
          </div>
        ))}
      </div>
    )
  }
  