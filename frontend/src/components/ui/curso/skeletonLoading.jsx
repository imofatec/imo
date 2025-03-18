export default function SkeletonLoading() {
  return (
    <div className="flex flex-col text-center max-w-64 font-semibold px-5 cursor-pointer mb-12 text-white">
      <div className="w-[216px] h-[121px] bg-gray-700 rounded animate-pulse mb-4"></div>

      <div className="w-3/4 h-6 bg-gray-700 rounded animate-pulse mx-auto mb-2"></div>

    
      <div className="flex justify-center items-center space-x-2">
        
        <div className="w-4 h-4 bg-gray-700 rounded-full animate-pulse"></div>
        <div className="w-4 h-4 bg-gray-700 rounded-full animate-pulse"></div>
        <div className="w-4 h-4 bg-gray-700 rounded-full animate-pulse"></div>
        <div className="w-4 h-4 bg-gray-700 rounded-full animate-pulse"></div>
        <div className="w-4 h-4 bg-gray-700 rounded-full animate-pulse"></div>

        
        <div className="w-12 h-4 bg-gray-700 rounded animate-pulse ml-2"></div>
      </div>
    </div>
  )
}
