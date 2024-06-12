class Solution {
    public int findPeakElement(int[] nums) {

        int[] nums1 = new int[nums.length+2];
        for(int i=0;i<nums.length;i++){
            nums1[i+1]=nums[i];
        }
        int left = 1;
        int right = nums.length;
        nums1[0]=Integer.MIN_VALUE;
        nums1[nums1.length-1]=Integer.MIN_VALUE;
        while(left<=right){
            int mid = left+(right-left)/2;
            if(nums1[mid]>nums1[mid-1]&&nums1[mid]>nums1[mid+1]){
                return mid-1;
            }else if(nums1[mid]<nums1[mid-1]){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return left-1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {1,2,3,1};
        System.out.println(solution.findPeakElement(nums));
    }
}